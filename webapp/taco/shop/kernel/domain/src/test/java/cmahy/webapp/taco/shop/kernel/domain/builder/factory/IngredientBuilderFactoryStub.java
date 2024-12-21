package cmahy.webapp.taco.shop.kernel.domain.builder.factory;

import cmahy.webapp.taco.shop.kernel.domain.IngredientStub;
import cmahy.webapp.taco.shop.kernel.domain.builder.IngredientBuilderStub;

public class IngredientBuilderFactoryStub implements IngredientBuilderFactory<IngredientStub> {

    @Override
    public IngredientBuilderStub create() {
        return new IngredientBuilderStub();
    }

    @Override
    public IngredientBuilderStub create(IngredientStub ingredient) {
        return new IngredientBuilderStub(ingredient);
    }
}
