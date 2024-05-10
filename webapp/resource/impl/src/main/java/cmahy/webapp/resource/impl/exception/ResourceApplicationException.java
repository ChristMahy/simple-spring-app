package cmahy.webapp.resource.impl.exception;

public abstract class ResourceApplicationException extends RuntimeException {

    public ResourceApplicationException() {
    }

    public ResourceApplicationException(String message) {
        super(message);
    }

    public ResourceApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceApplicationException(Throwable cause) {
        super(cause);
    }

    public ResourceApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
