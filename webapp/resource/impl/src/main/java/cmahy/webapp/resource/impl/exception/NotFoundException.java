package cmahy.webapp.resource.impl.exception;

public abstract class NotFoundException extends ResourceApplicationException {

    public NotFoundException(String message) {
        super(message);
    }
}
