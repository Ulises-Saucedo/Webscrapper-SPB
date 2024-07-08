package com.atlacademy.webscrapper.jobs;

import com.atlacademy.webscrapper.services.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class WebscrapperJob {

    @Autowired
    private SpiderService spiderService;

    @Scheduled(cron = "0 0 4 * * *")
    public void executeJob() {
        spiderService.start();
    }
}
