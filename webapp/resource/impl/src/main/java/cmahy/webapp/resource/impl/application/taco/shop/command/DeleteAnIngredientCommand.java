package cmahy.webapp.resource.impl.application.taco.shop.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.resource.impl.application.taco.shop.service.DeleteAnIngredient;
import cmahy.webapp.resource.taco.shop.id.IngredientId;
import jakarta.inject.Named;

@Command
@Named
public class DeleteAnIngredientCommand {

    private final DeleteAnIngredient deleteAnIngredient;

    public DeleteAnIngredientCommand(DeleteAnIngredient deleteAnIngredient) {
        this.deleteAnIngredient = deleteAnIngredient;
    }

    public void execute(IngredientId id) {
        deleteAnIngredient.execute(id);
    }
}
