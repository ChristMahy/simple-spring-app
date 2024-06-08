package cmahy.webapp.resource.impl.exception.taco;

import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.exception.DuplicateException;

public class IngredientDuplicateException extends DuplicateException {

    public IngredientDuplicateException(Ingredient ingredient) {
        super(String.format("Duplicate elements <%s> found", ingredient.getName()));
    }
}
