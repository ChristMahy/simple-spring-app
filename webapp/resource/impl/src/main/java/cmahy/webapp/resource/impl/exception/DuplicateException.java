package cmahy.webapp.resource.impl.exception;

public abstract class DuplicateException extends ResourceApplicationException {

    public DuplicateException(String message) {
        super(message);
    }
}
