package cmahy.webapp.resource.impl.exception;

public class NullException extends ResourceApplicationException {

    public NullException(Class<?> type) {
        super(type.getName() + " has not to be null");
    }
}
