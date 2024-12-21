package cmahy.webapp.taco.shop.adapter.jpa.entity.builder;

import cmahy.webapp.taco.shop.adapter.jpa.entity.JpaIngredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.builder.IngredientBuilder;

import java.util.Optional;

public class JpaIngredientBuilder implements IngredientBuilder<JpaIngredient> {

    private Optional<JpaIngredient> originalIngredient = Optional.empty();

    private String id;
    private String name;
    private IngredientType type;

    public JpaIngredientBuilder() {}

    public JpaIngredientBuilder(JpaIngredient ingredient) {
        this.originalIngredient = Optional.ofNullable(ingredient);

        this.originalIngredient.ifPresent(originalIngredient -> {
            this.id(originalIngredient.getId())
                .name(originalIngredient.getName())
                .type(originalIngredient.getType());
        });
    }

    @Override
    public JpaIngredientBuilder id(String id) {
        this.id = id;

        return this;
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
            .setId(this.id)
            .setName(this.name)
            .setType(this.type);
    }
}
