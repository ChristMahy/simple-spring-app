package cmahy.webapp.taco.shop.kernel.application.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.taco.shop.kernel.application.service.PartialUpdatingAnIngredient;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientNotFoundException;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientUpdateInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import jakarta.inject.Named;

@Command
@Named
public class PartialUpdatingAnIngredientCommand {

    private final PartialUpdatingAnIngredient partialUpdatingAnIngredient;

    public PartialUpdatingAnIngredientCommand(PartialUpdatingAnIngredient partialUpdatingAnIngredient) {
        this.partialUpdatingAnIngredient = partialUpdatingAnIngredient;
    }

    public IngredientOutputVo execute(IngredientId id, IngredientUpdateInputVo inputVo) throws IngredientNotFoundException, RequiredException {
        return partialUpdatingAnIngredient.execute(id, inputVo);
    }
}
