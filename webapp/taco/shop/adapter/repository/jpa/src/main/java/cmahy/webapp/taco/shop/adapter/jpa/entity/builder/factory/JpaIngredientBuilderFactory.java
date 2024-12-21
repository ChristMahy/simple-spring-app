package cmahy.webapp.taco.shop.adapter.jpa.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.jpa.entity.JpaIngredient;
import cmahy.webapp.taco.shop.adapter.jpa.entity.builder.JpaIngredientBuilder;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.IngredientBuilderFactory;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

@Primary
@Named
public class JpaIngredientBuilderFactory implements IngredientBuilderFactory<JpaIngredient> {

    @Override
    public JpaIngredientBuilder create() {
        return new JpaIngredientBuilder();
    }

    @Override
    public JpaIngredientBuilder create(JpaIngredient ingredient) {
        return new JpaIngredientBuilder(ingredient);
    }
}
