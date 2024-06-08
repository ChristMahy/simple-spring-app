package cmahy.webapp.resource.impl.exception.taco;

import cmahy.webapp.resource.impl.exception.ValidationException;

public class IngredientValidationException extends ValidationException {

    public IngredientValidationException(String message) {
        super(message);
    }
}
