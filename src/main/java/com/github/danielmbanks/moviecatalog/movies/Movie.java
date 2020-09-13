package com.github.danielmbanks.moviecatalog.movies;

import com.github.danielmbanks.moviecatalog.directors.Director;
import com.github.danielmbanks.moviecatalog.ratings.Rating;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @OneToOne
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    private Director director;

    @OneToOne
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    private Rating rating;

    @SuppressWarnings("unused")
    public Movie() {
        // no-args constructor for bean creation
    }

    public Movie(String title, Director director, Rating rating) {
        this.title = title;
        this.director = director;
        this.rating = rating;
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SuppressWarnings("unused")
    public Director getDirector() {
        return director;
    }

    @SuppressWarnings("unused")
    public void setDirector(Director director) {
        this.director = director;
    }

    @SuppressWarnings("unused")
    public Rating getRating() {
        return rating;
    }

    @SuppressWarnings("unused")
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(director, movie.director) &&
                Objects.equals(rating, movie.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, director, rating);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", director=" + director +
                ", rating=" + rating +
                '}';
    }
}
