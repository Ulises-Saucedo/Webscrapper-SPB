package com.atlacademy.webscrapper.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpiderService {

    @Autowired
    private WebscrapperService webscrapperService;

    public void start() {
        String initialLink = "https://elpais.com";
        scrapeLinksAndSave(initialLink);
    }

    public void scrapeLinksAndSave(String url) {
        List<String> links = webscrapperService.getAllLinks(url);

        links.stream().parallel().forEach(link -> {
            webscrapperService.scrapeAndSave(link);
            scrapeLinksAndSave(url);
        });
    }
}
