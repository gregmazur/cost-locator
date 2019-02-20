package org.open.budget.costlocator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration(exclude = { JacksonAutoConfiguration.class })
@EnableJpaRepositories(basePackages = "org.open.budget.costlocator.repository")
public class CostLocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CostLocatorApplication.class, args);
	}

}
