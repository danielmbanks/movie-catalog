package com.github.danielmbanks.moviecatalog.ratings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Rating {

    @Id
    @GeneratedValue
    private Long id;
    private Integer stars;

    @SuppressWarnings("unused")
    public Rating() {
        // no-args constructor for bean creation
    }

    public Rating(Integer stars) {
        this.stars = stars;
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(id, rating.id) &&
                Objects.equals(stars, rating.stars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stars);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", stars='" + stars + '\'' +
                '}';
    }
}
