package org.open.budget.costlocator;

import org.open.budget.costlocator.service.TenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Scheduler {
    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private TenderService tenderService;

    @Scheduled(cron = "0 0 23 * * ?")
    public void loadTenders() {
        log.warn("ATTENTION---STARTED TASK----------ATTENTION---------------ATTENTION---------------------");
        Extractor extractor = new Extractor(tenderService);
        extractor.extract();
    }
}
