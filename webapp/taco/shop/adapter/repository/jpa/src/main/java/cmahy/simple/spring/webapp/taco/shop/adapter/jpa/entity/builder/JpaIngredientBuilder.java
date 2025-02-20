package cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.builder;

import cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.domain.JpaIngredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.IngredientBuilder;

import java.util.Optional;

public class JpaIngredientBuilder implements IngredientBuilder<JpaIngredient> {

    private Optional<JpaIngredient> originalIngredient = Optional.empty();

    private String name;
    private IngredientType type;

    public JpaIngredientBuilder() {}

    public JpaIngredientBuilder(JpaIngredient ingredient) {
        this.originalIngredient = Optional.ofNullable(ingredient);

        this.originalIngredient.ifPresent(originalIngredient -> {
            this
                .name(originalIngredient.getName())
                .type(originalIngredient.getType());
        });
    }

    @Override
    public JpaIngredientBuilder name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public JpaIngredientBuilder type(IngredientType type) {
        this.type = type;

        return this;
    }

    @Override
    public JpaIngredient build() {
        return this.originalIngredient
            .orElseGet(JpaIngredient::new)
            .setName(this.name)
            .setType(this.type);
    }
}
