package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;

public interface IngredientBuilder<I extends Ingredient> {

    IngredientBuilder<I> name(String name);

    IngredientBuilder<I> type(IngredientType type);

    I build();
}
