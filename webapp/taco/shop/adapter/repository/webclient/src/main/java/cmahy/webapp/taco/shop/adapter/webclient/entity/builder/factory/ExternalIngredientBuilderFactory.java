package cmahy.webapp.taco.shop.adapter.webclient.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.webclient.entity.domain.ExternalIngredient;
import cmahy.webapp.taco.shop.adapter.webclient.entity.builder.ExternalIngredientBuilder;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.IngredientBuilderFactory;
import jakarta.inject.Named;

@Named
public class ExternalIngredientBuilderFactory implements IngredientBuilderFactory<ExternalIngredient> {

    @Override
    public ExternalIngredientBuilder create() {
        return new ExternalIngredientBuilder();
    }

    @Override
    public ExternalIngredientBuilder create(ExternalIngredient ingredient) {
        return new ExternalIngredientBuilder(ingredient);
    }
}
