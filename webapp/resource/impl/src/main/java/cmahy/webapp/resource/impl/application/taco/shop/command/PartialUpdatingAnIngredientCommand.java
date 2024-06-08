package cmahy.webapp.resource.impl.application.taco.shop.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.resource.impl.application.taco.shop.service.PartialUpdatingAnIngredient;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientUpdateInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import jakarta.inject.Named;

@Command
@Named
public class PartialUpdatingAnIngredientCommand {

    private final PartialUpdatingAnIngredient partialUpdatingAnIngredient;

    public PartialUpdatingAnIngredientCommand(PartialUpdatingAnIngredient partialUpdatingAnIngredient) {
        this.partialUpdatingAnIngredient = partialUpdatingAnIngredient;
    }

    public IngredientOutputAppVo execute(IngredientId id, IngredientUpdateInputAppVo inputAppVo) {
        return partialUpdatingAnIngredient.execute(id, inputAppVo);
    }
}
