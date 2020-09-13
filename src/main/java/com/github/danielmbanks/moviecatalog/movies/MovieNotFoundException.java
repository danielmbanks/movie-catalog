package com.github.danielmbanks.moviecatalog.movies;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(Long id) {
        super("Could not find movie " + id);
    }
}
