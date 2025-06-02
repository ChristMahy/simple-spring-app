package cmahy.simple.spring.webapp.reactive.resource.impl.exception.todo;

import cmahy.simple.spring.webapp.reactive.resource.impl.exception.TodoApplicationException;

public class TodoNotFoundException extends TodoApplicationException {

    public TodoNotFoundException(String message) {
        super(message);
    }

}
