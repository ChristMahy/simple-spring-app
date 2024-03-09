package cmahy.webapp.resource.impl.exception;

public class NullException extends RuntimeException {

    public NullException(Class<?> type) {
        super(type.getName() + " has not to be null");
    }
}
