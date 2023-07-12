package cmahy.springapp.reactive.controller;

import cmahy.springapp.reactive.domain.Todo;
import cmahy.springapp.reactive.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Optional;

@RestController
@RequestMapping(path = "/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public Flux<Todo> all(
        @RequestParam Optional<Long> offset,
        @RequestParam Optional<Long> limit
    ) {
        return todoService.all()
            .sort(Comparator.comparing(Todo::id))
            .skip(offset.orElse(0L))
            .take(limit.orElse(10L));
    }

    @GetMapping(path = "/{id}")
    public Mono<Todo> byId(@PathVariable Long id) {
        return todoService.byId(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Todo> create(@RequestBody Mono<Todo> todo) {
        return todo.flatMap(todoService::save);
    }
}
