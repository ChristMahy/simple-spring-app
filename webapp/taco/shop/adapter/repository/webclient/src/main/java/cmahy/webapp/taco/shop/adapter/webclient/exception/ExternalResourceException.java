package cmahy.webapp.taco.shop.adapter.webclient.exception;

public class ExternalResourceException extends RuntimeException {

    public ExternalResourceException(String message) {
        super(message);
    }

    public ExternalResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
