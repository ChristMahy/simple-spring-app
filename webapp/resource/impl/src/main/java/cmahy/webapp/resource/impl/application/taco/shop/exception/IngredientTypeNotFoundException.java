package cmahy.webapp.resource.impl.application.taco.shop.exception;

import cmahy.webapp.resource.impl.exception.NotFoundException;

public class IngredientTypeNotFoundException  extends NotFoundException {

    public IngredientTypeNotFoundException(String type) {
        super("Type <" + type + "> not found");
    }
}
