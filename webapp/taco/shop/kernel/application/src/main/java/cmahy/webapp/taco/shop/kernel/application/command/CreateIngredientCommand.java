package cmahy.webapp.taco.shop.kernel.application.command;

import cmahy.common.annotation.Command;
import cmahy.webapp.taco.shop.kernel.application.service.CreateAnIngredient;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientDuplicateException;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
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
    ) throws IngredientDuplicateException, RequiredException {
        return createAnIngredient.execute(ingredientInputVo);
    }
}
