package cmahy.simple.spring.webapp.taco.shop.kernel.application.command;

import cmahy.simple.spring.common.annotation.Command;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.service.DeleteAnIngredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.ingredient.IngredientUsageElementOnDeletionException;
import jakarta.inject.Named;

@Command
@Named
public class DeleteAnIngredientCommand {

    private final DeleteAnIngredient deleteAnIngredient;

    public DeleteAnIngredientCommand(DeleteAnIngredient deleteAnIngredient) {
        this.deleteAnIngredient = deleteAnIngredient;
    }

    public void execute(IngredientId id) throws IngredientUsageElementOnDeletionException {
        deleteAnIngredient.execute(id);
    }
}
