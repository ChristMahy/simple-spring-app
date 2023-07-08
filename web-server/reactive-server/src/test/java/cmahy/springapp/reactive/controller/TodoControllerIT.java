package cmahy.springapp.reactive.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoControllerIT {

    private WebTestClient testClient;

    @Autowired
    private TodoController todoController;

    @BeforeEach
    void setUp() {
        testClient = WebTestClient
            .bindToController(todoController)
            .build();
    }

    @Test
    void all() {
        assertDoesNotThrow(() -> {
            testClient.get().uri("/todo?limit=100")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty();
        });
    }
}