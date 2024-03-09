package cmahy.webapp.resource.impl.exception.taco;

import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NotFoundException;

public class IngredientNotFoundException extends NotFoundException {

    public IngredientNotFoundException(IngredientId id) {
        super(Ingredient.class.getName() + " <" + id.value() + "> not found");
    }
}
