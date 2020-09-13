package com.github.danielmbanks.moviecatalog.ratings;

public class RatingNotFoundException extends RuntimeException {

    public RatingNotFoundException(Long id) {
        super("Could not find rating " + id);
    }
}
