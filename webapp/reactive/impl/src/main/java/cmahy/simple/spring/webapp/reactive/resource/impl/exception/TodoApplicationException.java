package cmahy.simple.spring.webapp.reactive.resource.impl.exception;

public class TodoApplicationException extends Exception {

    public TodoApplicationException(String message) {
        super(message);
    }

    public TodoApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodoApplicationException(Throwable cause) {
        super(cause);
    }

}
