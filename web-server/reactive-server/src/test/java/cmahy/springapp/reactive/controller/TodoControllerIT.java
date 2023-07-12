package cmahy.springapp.reactive.controller;

import cmahy.springapp.reactive.domain.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@AutoConfigureWebTestClient
class TodoControllerIT {

    @Autowired
    private WebTestClient testClient;

    @Test
    void all() {
        assertDoesNotThrow(() -> {
            testClient.get().uri("/todo?limit=100")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Todo.class)
                .hasSize(100);
        });
    }

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            final Todo todo = new Todo(
                "New title...",
                "New description..."
            );

            Mono<Todo> unsavedTodo = Mono.just(todo);

            EntityExchangeResult<Todo> actualResult = testClient.post().uri("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedTodo, Todo.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Todo.class)
                .returnResult();

            assertThat(actualResult.getResponseBody()).isNotNull();

            assertThat(actualResult.getResponseBody().id()).isNotNull();
            assertThat(actualResult.getResponseBody().description()).isEqualTo(todo.description());
            assertThat(actualResult.getResponseBody().title()).isEqualTo(todo.title());
        });
    }
}