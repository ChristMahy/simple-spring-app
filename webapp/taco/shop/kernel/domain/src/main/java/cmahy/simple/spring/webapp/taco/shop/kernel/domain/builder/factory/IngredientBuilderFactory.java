package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.IngredientBuilder;

public interface IngredientBuilderFactory<I extends Ingredient> {

    IngredientBuilder<I> create();

    IngredientBuilder<I> create(I ingredient);
}
