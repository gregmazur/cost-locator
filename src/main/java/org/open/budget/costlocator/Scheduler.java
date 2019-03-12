package org.open.budget.costlocator;

import org.open.budget.costlocator.service.StreetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Scheduler {
    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private Extractor extractor;
    @Autowired
    private StreetService streetService;
    @Value( "${need.to.read.csv:true}" )
    private boolean needToReadStreetsFile;

//    @Scheduled(fixedDelay = 1000000)
    public void loadTenders() {
        log.warn("ATTENTION---STARTED TASK----------ATTENTION---------------ATTENTION---------------------");
        if (needToReadStreetsFile) {
            StreetReaderCsv readerServiceCsv = new StreetReaderCsv(streetService);
            log.warn("ATTENTION---STARTED READ FROM HOUSES----------ATTENTION---------------ATTENTION---------------------");
            readerServiceCsv.start();
        }
        extractor.extract();
    }
}
