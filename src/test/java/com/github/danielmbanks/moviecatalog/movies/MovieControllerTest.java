package com.github.danielmbanks.moviecatalog.movies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MovieController movieController;

    @Test
    public void getById_idExists_returnsStoredMovie() {
        // When
        Movie responseMovie = movieController.getById(1L);

        // Then
        assertEquals("Jurassic Park", responseMovie.getTitle());
    }

    @Test
    public void getById_idDoesNotExist_throwsNotFoundException() {
        // When
        Executable executable = () -> movieController.getById(999L);

        // Then
        MovieNotFoundException exception = Assertions.assertThrows(MovieNotFoundException.class, executable);
        assertEquals("Could not find movie 999", exception.getMessage());
    }

    @Test
    public void postMovie_validJson_storesAndReturnsMovie() {
        // Given
        String url = "http://localhost:" + port + "/movies";
        String requestBody = "{\"title\":\"Saving Private Ryan\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // When
        Movie responseMovie = restTemplate.postForObject(url, request, Movie.class);

        // Then
        assertNotNull(responseMovie.getId());
        assertEquals("Saving Private Ryan", responseMovie.getTitle());

        Movie storedMovie = movieController.getById(responseMovie.getId());
        assertEquals(responseMovie, storedMovie);
    }

    @Test
    public void replaceMovie_movieExists_storesAndReturnsMovie() {
        // Given
        String url = "http://localhost:" + port + "/movies/2";
        String requestBody = "{\"title\":\"Amistad\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<Movie> response = restTemplate.exchange(url, HttpMethod.PUT, request, Movie.class);

        // Then
        Movie responseMovie = response.getBody();
        assertNotNull(responseMovie);
        assertEquals(2L, responseMovie.getId());
        assertEquals("Amistad", responseMovie.getTitle());

        Movie storedMovie = movieController.getById(2L);
        assertEquals("Amistad", storedMovie.getTitle());
    }

    @Test
    public void replaceMovie_movieDoesNotExist_throwsNotFoundException() {
        // Given
        String url = "http://localhost:" + port + "/movies/500";
        String requestBody = "{\"title\":\"Minority Report\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        // Then
        assertEquals("Could not find movie 500", response.getBody());
    }

    @Test
    public void deleteMovie_validId_deletesMovie() {
        // Given
        Movie storedMovie = movieController.getById(3L);
        assertNotNull(storedMovie);
        String url = "http://localhost:" + port + "/movies/3";

        // When
        restTemplate.delete(url);

        // Then
        MovieNotFoundException exception = Assertions.assertThrows(
                MovieNotFoundException.class,
                () -> movieController.getById(3L));
        assertEquals("Could not find movie 3", exception.getMessage());
    }
}
