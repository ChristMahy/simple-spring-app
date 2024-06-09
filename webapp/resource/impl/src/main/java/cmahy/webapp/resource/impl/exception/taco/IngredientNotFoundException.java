package cmahy.webapp.resource.impl.exception.taco;

import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.exception.NotFoundException;
import cmahy.webapp.resource.taco.shop.id.IngredientId;

public class IngredientNotFoundException extends NotFoundException {

    public IngredientNotFoundException(IngredientId id) {
        super(Ingredient.class.getSimpleName() + " <" + id.value() + "> not found");
    }
}
