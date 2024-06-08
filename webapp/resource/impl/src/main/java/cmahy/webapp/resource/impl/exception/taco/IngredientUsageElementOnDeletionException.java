package cmahy.webapp.resource.impl.exception.taco;

import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.UsageOnDeletionException;

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
