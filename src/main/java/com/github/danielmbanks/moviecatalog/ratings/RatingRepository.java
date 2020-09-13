package com.github.danielmbanks.moviecatalog.ratings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
