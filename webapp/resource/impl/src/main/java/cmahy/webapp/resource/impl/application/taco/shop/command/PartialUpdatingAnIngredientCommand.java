package cmahy.webapp.resource.impl.application.taco.shop.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.resource.impl.application.taco.shop.service.PartialUpdatingAnIngredient;
import cmahy.webapp.resource.taco.shop.id.IngredientId;
import cmahy.webapp.resource.taco.shop.vo.input.IngredientUpdateInputVo;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
import jakarta.inject.Named;

@Command
@Named
public class PartialUpdatingAnIngredientCommand {

    private final PartialUpdatingAnIngredient partialUpdatingAnIngredient;

    public PartialUpdatingAnIngredientCommand(PartialUpdatingAnIngredient partialUpdatingAnIngredient) {
        this.partialUpdatingAnIngredient = partialUpdatingAnIngredient;
    }

    public IngredientOutputVo execute(IngredientId id, IngredientUpdateInputVo inputVo) {
        return partialUpdatingAnIngredient.execute(id, inputVo);
    }
}
