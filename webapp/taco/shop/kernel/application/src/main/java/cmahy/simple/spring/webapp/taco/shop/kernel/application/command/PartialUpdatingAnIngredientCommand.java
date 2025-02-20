package cmahy.simple.spring.webapp.taco.shop.kernel.application.command;

import cmahy.simple.spring.common.annotation.Command;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.service.PartialUpdatingAnIngredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.ingredient.IngredientNotFoundException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.IngredientUpdateInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
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
