package com.github.danielmbanks.moviecatalog;

import com.github.danielmbanks.moviecatalog.directors.Director;
import com.github.danielmbanks.moviecatalog.directors.DirectorRepository;
import com.github.danielmbanks.moviecatalog.movies.Movie;
import com.github.danielmbanks.moviecatalog.movies.MovieRepository;
import com.github.danielmbanks.moviecatalog.ratings.Rating;
import com.github.danielmbanks.moviecatalog.ratings.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(MovieRepository movieRepository,
                                          DirectorRepository directorRepository,
                                          RatingRepository ratingRepository) {
        return args -> {
            log.info("Preloading " + movieRepository.save(new Movie("Jurassic Park")));
            log.info("Preloading " + movieRepository.save(new Movie("Star Wars")));
            log.info("Preloading " + movieRepository.save(new Movie("Indiana Jones")));

            log.info("Preloading " + directorRepository.save(new Director("Stephen Spielberg")));
            log.info("Preloading " + directorRepository.save(new Director("George Lucas")));
            log.info("Preloading " + directorRepository.save(new Director("Ridley Scott")));

            log.info("Preloading " + ratingRepository.save(new Rating("1")));
            log.info("Preloading " + ratingRepository.save(new Rating("2")));
            log.info("Preloading " + ratingRepository.save(new Rating("3")));
        };
    }
}
