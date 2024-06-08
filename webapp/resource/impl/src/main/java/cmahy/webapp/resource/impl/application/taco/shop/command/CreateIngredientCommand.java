package cmahy.webapp.resource.impl.application.taco.shop.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.resource.impl.application.taco.shop.service.CreateAnIngredient;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientCreateInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
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

    public IngredientOutputAppVo execute(
        @NotNull(message = I18N_KEY_CREATE_INGREDIENT_NOT_NULL)
        IngredientCreateInputAppVo ingredientInputAppVo
    ) {
        return createAnIngredient.execute(ingredientInputAppVo);
    }
}
