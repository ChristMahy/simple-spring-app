package cmahy.webapp.taco.shop.kernel.domain.builder;

import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;

public interface IngredientBuilder<I extends Ingredient> {

    IngredientBuilder<I> id(String id);

    IngredientBuilder<I> name(String name);

    IngredientBuilder<I> type(IngredientType type);

    I build();
}
