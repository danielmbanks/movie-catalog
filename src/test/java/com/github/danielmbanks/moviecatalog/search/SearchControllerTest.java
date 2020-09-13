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
        assertEquals("[{\"id\":8,\"title\":\"Gladiator\",\"director\":{\"id\":3,\"name\":\"Ridley Scott\"}}," +
                        "{\"id\":9,\"title\":\"Kingdom of Heaven\",\"director\":{\"id\":3,\"name\":\"Ridley Scott\"}}]",
                response);
    }
}