package cmahy.webapp.taco.shop.adapter.webclient.entity.builder;

import cmahy.webapp.taco.shop.adapter.webclient.entity.ExternalIngredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.builder.IngredientBuilder;

import java.util.Optional;

public class ExternalIngredientBuilder implements IngredientBuilder<ExternalIngredient> {
    
    private Optional<ExternalIngredient> originalIngredient = Optional.empty();

    private String id;
    private String name;
    private IngredientType type;

    public ExternalIngredientBuilder() {}

    public ExternalIngredientBuilder(ExternalIngredient ingredient) {
        this.originalIngredient = Optional.ofNullable(ingredient);

        this.originalIngredient.ifPresent(originalIngredient -> {
            this.id(originalIngredient.getId())
                .name(originalIngredient.getName())
                .type(originalIngredient.getType());
        });
    }

    @Override
    public ExternalIngredientBuilder id(String id) {
        this.id = id;

        return this;
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
            .setId(this.id)
            .setName(this.name)
            .setType(this.type);
    }
}
