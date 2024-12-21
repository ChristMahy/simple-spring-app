package cmahy.webapp.taco.shop.kernel.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface Ingredient {

    String I18N_KEY_NOT_NULL = "validation.error.ingredient.not-null";

    String I18N_KEY_NAME_NOT_NULL = "validation.error.ingredient.name.not-null";
    String I18N_KEY_NAME_NOT_BLANK = "validation.error.ingredient.name.not-blank";

    String I18N_KEY_TYPE_NOT_NULL = "validation.error.ingredient.type.not-null";

    String getId();

    @NotNull(message = I18N_KEY_NAME_NOT_NULL)
    @NotBlank(message = I18N_KEY_NAME_NOT_BLANK)
    String getName();

    @NotNull(message = I18N_KEY_TYPE_NOT_NULL)
    IngredientType getType();
}
