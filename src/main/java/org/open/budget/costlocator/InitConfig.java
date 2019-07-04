package org.open.budget.costlocator;

import lombok.extern.slf4j.Slf4j;
import org.open.budget.costlocator.service.StreetService;
import org.open.budget.costlocator.service.TenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.open.budget.costlocator.Extractor.LIST_PATH_ID;
import static org.open.budget.costlocator.StreetReaderCsv.HOUSES_LOADING;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
@Slf4j
public class InitConfig {

    @Value("${houses.csv.name:houses.csv}")
    private String housesCsvName;

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    CommandLineRunner loadStreets(StreetService streetService, TenderService tenderService) {
        return args -> {
            String version = tenderService.getProperty(HOUSES_LOADING);
            StreetReaderCsv readerServiceCsv = new StreetReaderCsv(streetService, tenderService, housesCsvName);
            readerServiceCsv.start();

            String listPath = tenderService.getProperty(LIST_PATH_ID);
            if (listPath == null) {
                log.info("Writing in DB " + tenderService.saveProp(LIST_PATH_ID, "/api/2.4/tenders").getProperty());
            }
            Extractor extractor = new Extractor(tenderService);
            extractor.extract();
        };
    }
}
