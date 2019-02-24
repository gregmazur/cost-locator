package org.open.budget.costlocator.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Scheduler {
    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    Extractor extractor;

    @Scheduled(fixedDelay = 10000)
    public void loadTenders() {
        log.warn("ATTENTION---STARTED TASK----------ATTENTION---------------ATTENTION---------------------");
        extractor.extract();
    }
}
