package cmahy.webapp.taco.shop.kernel.exception.ingredient;

import cmahy.webapp.taco.shop.kernel.exception.ValidationException;

public class IngredientValidationException extends ValidationException {

    public IngredientValidationException(String message) {
        super(message);
    }
}
