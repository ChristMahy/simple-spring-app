package cmahy.simple.spring.webapp.taco.shop.kernel.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.*;

public interface Taco {

    String I18N_KEY_NAME_NOT_NULL = "validation.error.taco.name.not-null";
    String I18N_KEY_NAME_MIN_SIZE = "validation.error.taco.name.min-size";

    String I18N_KEY_INGREDIENTS_MIN_SIZE = "validation.error.taco.ingredients.min-size";

    UUID getId();

    Date getCreatedAt();

    @NotNull(message = I18N_KEY_NAME_NOT_NULL)
    @Size(min = 5, message = I18N_KEY_NAME_MIN_SIZE)
    String getName();

    @Size(min = 1, message = I18N_KEY_INGREDIENTS_MIN_SIZE)
    <I extends Ingredient> Collection<I> getIngredients();

    void addIngredient(Ingredient ingredient);
}
