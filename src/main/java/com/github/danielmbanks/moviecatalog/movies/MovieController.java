package com.github.danielmbanks.moviecatalog.movies;

import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController {

    private final MovieRepository repository;

    public MovieController(MovieRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/movies")
    public Movie post(@RequestBody Movie newMovie) {
        return repository.save(newMovie);
    }

    // Purely used for testing, not exposed as part of the REST API
    Movie getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @PutMapping("/movies/{id}")
    public Movie update(@RequestBody Movie newMovie, @PathVariable Long id) {
        return repository.findById(id)
                .map(movie -> {
                    movie.setTitle(newMovie.getTitle());
                    return repository.save(movie);
                })
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @DeleteMapping("/movies/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
