package cmahy.simple.spring.webapp.taco.shop.kernel.exception.ingredient;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.NotFoundException;

public class IngredientNotFoundException extends NotFoundException {

    public IngredientNotFoundException(IngredientId id) {
        super(Ingredient.class.getSimpleName() + " <" + id.value() + "> not found");
    }
}
