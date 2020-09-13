package com.github.danielmbanks.moviecatalog.ratings;

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
public class RatingControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RatingController ratingController;

    @Test
    public void getById_idExists_returnsStoredRating() {
        // When
        Rating responseRating = ratingController.getById(5L);

        // Then
        assertEquals(1, responseRating.getStars());
    }

    @Test
    public void getById_idDoesNotExist_throwsNotFoundException() {
        // When
        Executable executable = () -> ratingController.getById(999L);

        // Then
        RatingNotFoundException exception = Assertions.assertThrows(RatingNotFoundException.class, executable);
        assertEquals("Could not find rating 999", exception.getMessage());
    }

    @Test
    public void postRating_validJson_storesAndReturnsRating() {
        // Given
        String url = "http://localhost:" + port + "/ratings";
        String requestBody = "{\"stars\":\"4\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // When
        Rating responseRating = restTemplate.postForObject(url, request, Rating.class);

        // Then
        assertNotNull(responseRating.getId());
        assertEquals(4, responseRating.getStars());

        Rating storedRating = ratingController.getById(responseRating.getId());
        assertEquals(responseRating, storedRating);
    }

    @Test
    public void replaceRating_ratingExists_storesAndReturnsRating() {
        // Given
        String url = "http://localhost:" + port + "/ratings/6";
        String requestBody = "{\"stars\":\"1\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<Rating> response = restTemplate.exchange(url, HttpMethod.PUT, request, Rating.class);

        // Then
        Rating responseRating = response.getBody();
        assertNotNull(responseRating);
        assertEquals(6L, responseRating.getId());
        assertEquals(1, responseRating.getStars());

        Rating storedRating = ratingController.getById(6L);
        assertEquals(1, storedRating.getStars());
    }

    @Test
    public void replaceRating_ratingDoesNotExist_throwsNotFoundException() {
        // Given
        String url = "http://localhost:" + port + "/ratings/500";
        String requestBody = "{\"stars\":\"2\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        // Then
        assertEquals("Could not find rating 500", response.getBody());
    }

    @Test
    public void deleteRating_validId_deletesRating() {
        // Given
        Rating storedRating = ratingController.getById(10L);
        assertNotNull(storedRating);
        String url = "http://localhost:" + port + "/ratings/10";

        // When
        restTemplate.delete(url);

        // Then
        RatingNotFoundException exception = Assertions.assertThrows(
                RatingNotFoundException.class,
                () -> ratingController.getById(10L));
        assertEquals("Could not find rating 10", exception.getMessage());
    }
}
