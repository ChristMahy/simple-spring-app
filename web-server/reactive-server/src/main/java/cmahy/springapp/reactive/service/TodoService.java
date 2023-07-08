package cmahy.springapp.reactive.service;

import cmahy.springapp.reactive.domain.Todo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final Set<Todo> todos = new HashSet<>();

    public Todo save(Todo todo) {
        Long lastId = this.todos.stream().max(Comparator.comparingLong(Todo::getId)).map(Todo::getId).orElse(0L);

        todo.setId(lastId + 1);

        this.todos.add(todo);

        return todo;
    }

    public Set<Todo> all() {
        return this.todos;
    }

    public Optional<Todo> byId(Long id) {
        Set<Todo> todosFiltered = todos.stream()
            .filter(todo -> Objects.equals(id, todo.getId()))
            .collect(Collectors.toSet());

        return todosFiltered.size() == 1 ? todosFiltered.stream().findFirst() : Optional.empty();
    }
}
