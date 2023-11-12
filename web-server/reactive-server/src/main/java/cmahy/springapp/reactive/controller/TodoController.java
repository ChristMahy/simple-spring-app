package cmahy.springapp.reactive.controller;

import cmahy.springapp.reactive.domain.Todo;
import cmahy.springapp.reactive.service.TodoService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Comparator;
import java.util.Optional;

@RestController
@RequestMapping(path = "/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_NDJSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Flux<Todo> all(
        @RequestParam Optional<Long> offset,
        @RequestParam Optional<Long> limit
    ) {
        return todoService.all()
            .sort(Comparator.comparing(Todo::id))
            .log()
            .delayElements(Duration.ofSeconds(1))
            .skip(offset.orElse(0L))
            .take(limit.orElse(10L));
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_NDJSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Mono<Todo> byId(@PathVariable Long id) {
        return todoService.byId(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_NDJSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Todo> create(@RequestBody Mono<Todo> todo) {
        return todo.flatMap(todoService::save);
    }
}
