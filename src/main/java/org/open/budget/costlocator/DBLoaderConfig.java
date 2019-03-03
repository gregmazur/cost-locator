package org.open.budget.costlocator;

import lombok.extern.slf4j.Slf4j;
import org.open.budget.costlocator.entity.ListPath;
import org.open.budget.costlocator.repository.ListPathRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityNotFoundException;

@Configuration
@Slf4j
public class DBLoaderConfig {

    @Bean
    CommandLineRunner initDatabase(ListPathRepository repository) {
        return args -> {
            try {
                repository.getOne(1L);
            } catch (EntityNotFoundException e){
                log.info("Preloading " + repository.save(ListPath.builder().id(1L).lastPath("/api/2.4/tenders").build()).getLastPath());
            }
        };
    }
}
