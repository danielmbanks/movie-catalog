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
            Director spielberg = new Director("Stephen Spielberg");
            log.info("Preloading " + directorRepository.save(spielberg));
            Director lucas = new Director("George Lucas");
            log.info("Preloading " + directorRepository.save(lucas));
            Director scott = new Director("Ridley Scott");
            log.info("Preloading " + directorRepository.save(scott));
            Director burton = new Director("Tim Burton");
            log.info("Preloading " + directorRepository.save(burton));

            log.info("Preloading " + movieRepository.save(new Movie("Jurassic Park", spielberg)));
            log.info("Preloading " + movieRepository.save(new Movie("Star Wars", lucas)));
            log.info("Preloading " + movieRepository.save(new Movie("Indiana Jones", spielberg)));
            log.info("Preloading " + movieRepository.save(new Movie("Gladiator", scott)));
            log.info("Preloading " + movieRepository.save(new Movie("Kingdom of Heaven", scott)));

            log.info("Preloading " + ratingRepository.save(new Rating("1")));
            log.info("Preloading " + ratingRepository.save(new Rating("2")));
            log.info("Preloading " + ratingRepository.save(new Rating("3")));
        };
    }
}
