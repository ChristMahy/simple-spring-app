package cmahy.webapp.resource.impl.exception;

public abstract class ValidationException  extends ResourceApplicationException {

    public ValidationException(String message) {
        super(message);
    }
}
