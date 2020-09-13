package com.github.danielmbanks.moviecatalog.search;

import com.github.danielmbanks.moviecatalog.directors.Director;
import com.github.danielmbanks.moviecatalog.directors.DirectorRepository;
import com.github.danielmbanks.moviecatalog.movies.Movie;
import com.github.danielmbanks.moviecatalog.movies.MovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
public class SearchController {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;

    public SearchController(MovieRepository movieRepository, DirectorRepository directorRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
    }

    @GetMapping("search")
    public Collection<Movie> findByDirector(@RequestParam Long directorId) {
        Optional<Director> optionalDirector = directorRepository.findById(directorId);
        if (optionalDirector.isPresent()) {
            return movieRepository.findByDirector(optionalDirector.get());
        }
        return Collections.emptyList();

    }
}
