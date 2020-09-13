package com.github.danielmbanks.moviecatalog.directors;

import org.springframework.web.bind.annotation.*;

@RestController
public class DirectorController {

    private final DirectorRepository repository;

    public DirectorController(DirectorRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/directors")
    public Director post(@RequestBody Director newDirector) {
        return repository.save(newDirector);
    }

    // Purely used for testing, not exposed as part of the REST API
    Director getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DirectorNotFoundException(id));
    }

    @PutMapping("/directors/{id}")
    public Director update(@RequestBody Director newDirector, @PathVariable Long id) {
        return repository.findById(id)
                .map(director -> {
                    director.setName(newDirector.getName());
                    return repository.save(director);
                })
                .orElseThrow(() -> new DirectorNotFoundException(id));
    }

    @DeleteMapping("/directors/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
