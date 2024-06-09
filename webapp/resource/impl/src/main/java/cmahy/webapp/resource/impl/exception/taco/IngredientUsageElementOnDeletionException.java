package cmahy.webapp.resource.impl.exception.taco;

import cmahy.webapp.resource.impl.exception.UsageOnDeletionException;
import cmahy.webapp.resource.taco.shop.id.IngredientId;

import java.util.Optional;

public class IngredientUsageElementOnDeletionException extends UsageOnDeletionException {

    public IngredientUsageElementOnDeletionException(IngredientId id) {
        super(String.format(
            "Couldn't delete element <%s>, element is currently used",
            Optional.ofNullable(id)
                .map(IngredientId::value)
                .orElse("???NONE???")
        ));
    }
}
