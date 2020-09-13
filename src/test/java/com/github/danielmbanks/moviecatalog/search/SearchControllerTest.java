package com.github.danielmbanks.moviecatalog.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SearchControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findByDirectorId() {
        // Given
        String url = "http://localhost:" + port + "/search?directorId=3";

        // When
        String response = restTemplate.getForObject(url, String.class);

        // Then
        assertEquals("[{\"id\":14,\"title\":\"Gladiator\"," +
                        "\"director\":{\"id\":3,\"name\":\"Ridley Scott\"}," +
                        "\"rating\":{\"id\":7,\"stars\":3}}," +
                        "{\"id\":15,\"title\":\"Kingdom of Heaven\"," +
                        "\"director\":{\"id\":3,\"name\":\"Ridley Scott\"}," +
                        "\"rating\":{\"id\":9,\"stars\":5}}]",
                response);
    }

    @Test
    public void findByRatingAbove3() {
        // Given
        String url = "http://localhost:" + port + "/search?ratingHigherThan=3";

        // When
        String response = restTemplate.getForObject(url, String.class);

        // Then
        assertEquals("[{\"id\":13,\"title\":\"Indiana Jones\"," +
                        "\"director\":{\"id\":1,\"name\":\"Stephen Spielberg\"}," +
                        "\"rating\":{\"id\":8,\"stars\":4}}," +
                        "{\"id\":15,\"title\":\"Kingdom of Heaven\"," +
                        "\"director\":{\"id\":3,\"name\":\"Ridley Scott\"}," +
                        "\"rating\":{\"id\":9,\"stars\":5}}]",
                response);
    }
}