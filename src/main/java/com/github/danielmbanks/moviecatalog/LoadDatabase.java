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

            Rating oneStar = new Rating(1);
            log.info("Preloading " + ratingRepository.save(oneStar));
            Rating twoStars = new Rating(2);
            log.info("Preloading " + ratingRepository.save(twoStars));
            Rating threeStars = new Rating(3);
            log.info("Preloading " + ratingRepository.save(threeStars));
            Rating fourStars = new Rating(4);
            log.info("Preloading " + ratingRepository.save(fourStars));
            Rating fiveStars = new Rating(5);
            log.info("Preloading " + ratingRepository.save(fiveStars));
            Rating fiveStars2 = new Rating(5);
            log.info("Preloading " + ratingRepository.save(fiveStars2));

            log.info("Preloading " + movieRepository.save(new Movie("Jurassic Park", spielberg, oneStar)));
            log.info("Preloading " + movieRepository.save(new Movie("Star Wars", lucas, twoStars)));
            log.info("Preloading " + movieRepository.save(new Movie("Indiana Jones", spielberg, fourStars)));
            log.info("Preloading " + movieRepository.save(new Movie("Gladiator", scott, threeStars)));
            log.info("Preloading " + movieRepository.save(new Movie("Kingdom of Heaven", scott, fiveStars)));
            log.info("Preloading " + movieRepository.save(new Movie("American Graffiti", lucas, oneStar)));
        };
    }
}
