package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.builder;

import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.domain.ExternalIngredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.IngredientBuilder;

import java.util.Optional;

public class ExternalIngredientBuilder implements IngredientBuilder<ExternalIngredient> {
    
    private Optional<ExternalIngredient> originalIngredient = Optional.empty();

    private String name;
    private IngredientType type;

    public ExternalIngredientBuilder() {}

    public ExternalIngredientBuilder(ExternalIngredient ingredient) {
        this.originalIngredient = Optional.ofNullable(ingredient);

        this.originalIngredient.ifPresent(originalIngredient -> {
            this
                .name(originalIngredient.getName())
                .type(originalIngredient.getType());
        });
    }

    @Override
    public ExternalIngredientBuilder name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public ExternalIngredientBuilder type(IngredientType type) {
        this.type = type;

        return this;
    }

    @Override
    public ExternalIngredient build() {
        return this.originalIngredient
            .orElseGet(ExternalIngredient::new)
            .setName(this.name)
            .setType(this.type);
    }
}
