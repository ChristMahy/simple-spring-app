package cmahy.webapp.resource.impl.exception.taco;

import cmahy.webapp.resource.impl.domain.taco.IngredientType;
import cmahy.webapp.resource.impl.exception.NotFoundException;

public class IngredientTypeNotFoundException extends NotFoundException {

    public IngredientTypeNotFoundException(String type) {
        super(IngredientType.class.getSimpleName() + " <" + type + "> not found");
    }
}
