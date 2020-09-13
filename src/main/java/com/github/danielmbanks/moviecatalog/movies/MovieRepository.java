package com.github.danielmbanks.moviecatalog.movies;

import com.github.danielmbanks.moviecatalog.directors.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByDirector(Director director);

}
