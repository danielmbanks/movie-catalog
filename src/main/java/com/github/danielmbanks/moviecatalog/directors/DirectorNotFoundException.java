package com.github.danielmbanks.moviecatalog.directors;

public class DirectorNotFoundException extends RuntimeException {

    public DirectorNotFoundException(Long id) {
        super("Could not find director " + id);
    }
}
