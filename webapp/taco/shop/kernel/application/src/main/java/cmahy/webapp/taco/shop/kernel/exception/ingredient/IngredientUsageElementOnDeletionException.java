package cmahy.webapp.taco.shop.kernel.exception.ingredient;

import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.exception.UsageOnDeletionException;

import java.util.Optional;
import java.util.UUID;

public class IngredientUsageElementOnDeletionException extends UsageOnDeletionException {

    public IngredientUsageElementOnDeletionException(IngredientId id) {
        super(String.format(
            "Couldn't delete element <%s>, element is currently used",
            Optional.ofNullable(id)
                .map(IngredientId::value)
                .map(UUID::toString)
                .orElse("???NONE???")
        ));
    }
}
