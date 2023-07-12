package cmahy.springapp.reactive.service;

import cmahy.springapp.reactive.domain.Todo;
import cmahy.springapp.reactive.repository.TodoRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class TodoService {

    private static final Logger LOG = getLogger(TodoService.class);

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Mono<Todo> save(Todo todo) {
        return todoRepository.save(todo);
    }

    public Flux<Todo> all() {
        return this.todoRepository
            .findAll()
            .doOnNext(todo -> {
                LOG.debug("Todo: <{}>", todo);
            });
    }

    public Mono<Todo> byId(Long id) {
        return todoRepository.findById(id);
    }
}
