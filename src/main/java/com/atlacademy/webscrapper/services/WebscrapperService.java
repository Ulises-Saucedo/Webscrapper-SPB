package com.atlacademy.webscrapper.services;

import com.atlacademy.webscrapper.models.Webpage;
import com.atlacademy.webscrapper.repositories.WebpageRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebscrapperService {

    @Autowired
    private WebpageRepository repository;

    public void scrapeAndSave(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            String domain = getDomainFromUrl(url);
            String title = document.title();
            String description = document.select("meta[name=description]").attr("content");
            String picture = document.select("meta[property=og:image]").attr("content");

            Webpage webpage = new Webpage();
            webpage.setTitle(title);
            webpage.setDescription(description);
            webpage.setPicture(picture);
            webpage.setDomain(domain);
            webpage.setUrl(url);

            repository.save(webpage);
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }
    }

    private String getDomainFromUrl(String url) {
        String domain = url.replaceFirst("^(https?://)?(www\\.)?", "");
        int index = domain.indexOf("/");
        if(index != -1) {
            domain = domain.substring(0, index);
        }

        return domain;
    }

    public List<String> getAllLinks(String url) {
        List<String> result = new ArrayList<>();

        Webpage findWebpage = repository.findByUrl(url);
        if(findWebpage != null) {
           return result;
        }

        try {
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("a[href]");

            links.stream().parallel().forEach(link -> {
                String linkHref = link.attr("href");

                if(linkHref.startsWith("/")) {
                    linkHref = "https://" + getDomainFromUrl(url) + linkHref;
                }

                if(!result.contains(linkHref)) {
                    result.add(linkHref);
                }
            });
        } catch(IOException error) {
            System.out.println(error.getMessage());
        }

        return result;
    }
}
