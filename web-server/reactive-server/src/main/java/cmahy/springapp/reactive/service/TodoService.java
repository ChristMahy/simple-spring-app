package cmahy.springapp.reactive.service;

import cmahy.springapp.reactive.domain.Todo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TodoService {

    private final Set<Todo> todos = new HashSet<>();

    public Todo save(Todo todo) {
        this.todos.add(todo);

        return todo;
    }

    public Set<Todo> all() {
        return this.todos;
    }
}
