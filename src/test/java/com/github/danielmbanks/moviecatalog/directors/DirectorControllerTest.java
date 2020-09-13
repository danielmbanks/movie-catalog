package com.github.danielmbanks.moviecatalog.directors;

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
public class DirectorControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DirectorController directorController;

    @Test
    public void getById_idExists_returnsStoredDirector() {
        // When
        Director responseDirector = directorController.getById(4L);

        // Then
        assertEquals("Stephen Spielberg", responseDirector.getName());
    }

    @Test
    public void getById_idDoesNotExist_throwsNotFoundException() {
        // When
        Executable executable = () -> directorController.getById(999L);

        // Then
        DirectorNotFoundException exception = Assertions.assertThrows(DirectorNotFoundException.class, executable);
        assertEquals("Could not find director 999", exception.getMessage());
    }

    @Test
    public void postDirector_validJson_storesAndReturnsDirector() {
        // Given
        String url = "http://localhost:" + port + "/directors";
        String requestBody = "{\"name\":\"Martin Scorcese\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // When
        Director responseDirector = restTemplate.postForObject(url, request, Director.class);

        // Then
        assertNotNull(responseDirector.getId());
        assertEquals("Martin Scorcese", responseDirector.getName());

        Director storedDirector = directorController.getById(responseDirector.getId());
        assertEquals(responseDirector, storedDirector);
    }

    @Test
    public void replaceDirector_directorExists_storesAndReturnsDirector() {
        // Given
        String url = "http://localhost:" + port + "/directors/5";
        String requestBody = "{\"name\":\"Christopher Nolan\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<Director> response = restTemplate.exchange(url, HttpMethod.PUT, request, Director.class);

        // Then
        Director responseDirector = response.getBody();
        assertNotNull(responseDirector);
        assertEquals(5L, responseDirector.getId());
        assertEquals("Christopher Nolan", responseDirector.getName());

        Director storedDirector = directorController.getById(5L);
        assertEquals("Christopher Nolan", storedDirector.getName());
    }

    @Test
    public void replaceDirector_directorDoesNotExist_throwsNotFoundException() {
        // Given
        String url = "http://localhost:" + port + "/directors/500";
        String requestBody = "{\"name\":\"Richard Linklater\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // When
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        // Then
        assertEquals("Could not find director 500", response.getBody());
    }

    @Test
    public void deleteDirector_validId_deletesDirector() {
        // Given
        Director storedDirector = directorController.getById(6L);
        assertNotNull(storedDirector);
        String url = "http://localhost:" + port + "/directors/6";

        // When
        restTemplate.delete(url);

        // Then
        DirectorNotFoundException exception = Assertions.assertThrows(
                DirectorNotFoundException.class,
                () -> directorController.getById(6L));
        assertEquals("Could not find director 6", exception.getMessage());
    }
}
