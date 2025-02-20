package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.exception;

public class IngredientExternalResourceException extends ExternalResourceException {

    public IngredientExternalResourceException(String message) {
        super(message);
    }

    public IngredientExternalResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
