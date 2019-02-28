package org.open.budget.costlocator;

import lombok.extern.slf4j.Slf4j;
import org.open.budget.costlocator.api.ListPath;
import org.open.budget.costlocator.repository.ListPathRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
@Slf4j
public class DBLoaderConfig {

    @Bean
    CommandLineRunner initDatabase(ListPathRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(ListPath.builder().id(1L).lastPath("/api/2.4/tenders").build()).getLastPath());
        };
    }
}
