package cmahy.webapp.resource.impl.exception.taco;

import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.exception.NotFoundException;

public class IngredientTypeNotFoundException extends NotFoundException {

    public IngredientTypeNotFoundException(String type) {
        super(Ingredient.Type.class.getSimpleName() + " <" + type + "> not found");
    }
}
