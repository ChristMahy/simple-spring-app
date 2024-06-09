package cmahy.webapp.resource.impl.application.taco.shop.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.resource.impl.application.taco.shop.service.CreateAnIngredient;
import cmahy.webapp.resource.taco.shop.vo.input.IngredientCreateInputVo;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;

@Command
@Named
public class CreateIngredientCommand {

    public static final String I18N_KEY_CREATE_INGREDIENT_NOT_NULL = "validation.error.ingredient.create.input.not-null";

    private final CreateAnIngredient createAnIngredient;

    public CreateIngredientCommand(CreateAnIngredient createAnIngredient) {
        this.createAnIngredient = createAnIngredient;
    }

    public IngredientOutputVo execute(
        @NotNull(message = I18N_KEY_CREATE_INGREDIENT_NOT_NULL)
        IngredientCreateInputVo ingredientInputVo
    ) {
        return createAnIngredient.execute(ingredientInputVo);
    }
}
