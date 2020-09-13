package com.github.danielmbanks.moviecatalog.ratings;

import org.springframework.web.bind.annotation.*;

@RestController
public class RatingController {

    private final RatingRepository repository;

    public RatingController(RatingRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/ratings")
    public Rating post(@RequestBody Rating newRating) {
        return repository.save(newRating);
    }

    // Purely used for testing, not exposed as part of the REST API
    Rating getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(id));
    }

    @PutMapping("/ratings/{id}")
    public Rating update(@RequestBody Rating newRating, @PathVariable Long id) {
        return repository.findById(id)
                .map(rating -> {
                    rating.setStars(newRating.getStars());
                    return repository.save(rating);
                })
                .orElseThrow(() -> new RatingNotFoundException(id));
    }

    @DeleteMapping("/ratings/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
