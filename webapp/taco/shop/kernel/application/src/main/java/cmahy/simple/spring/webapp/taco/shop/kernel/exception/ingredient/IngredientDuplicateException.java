package cmahy.simple.spring.webapp.taco.shop.kernel.exception.ingredient;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.DuplicateException;

public class IngredientDuplicateException extends DuplicateException {

    public IngredientDuplicateException(Ingredient ingredient) {
        super(String.format("Duplicate elements <%s> found", ingredient.getName()));
    }
}
