package com.github.danielmbanks.moviecatalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(MovieRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Movie("Jurassic Park")));
            log.info("Preloading " + repository.save(new Movie("Star Wars")));
            log.info("Preloading " + repository.save(new Movie("Indiana Jones")));
        };
    }
}
