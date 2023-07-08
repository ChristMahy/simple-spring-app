package cmahy.springapp.reactive.controller;

import cmahy.springapp.reactive.domain.Todo;
import cmahy.springapp.reactive.service.TodoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@RestController
@RequestMapping(path = "/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public Flux<Todo> all() {
        return Flux.fromIterable(todoService.all()).sort(Comparator.comparing(Todo::getId)).skip(20).take(10);
    }

    @GetMapping(path = "/{id}")
    public Mono<Todo> byId(@PathVariable Long id) {
        return Mono.justOrEmpty(todoService.byId(id));
    }
}
