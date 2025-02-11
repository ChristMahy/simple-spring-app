package cmahy.webapp.taco.shop.kernel.domain.builder;

import cmahy.webapp.taco.shop.kernel.domain.IngredientStub;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;

import java.util.Optional;

public class IngredientBuilderStub implements IngredientBuilder<IngredientStub> {

    private Optional<IngredientStub> originalIngredient = Optional.empty();

    private String name;
    private IngredientType type;

    public IngredientBuilderStub() {}

    public IngredientBuilderStub(IngredientStub ingredient) {
        this.originalIngredient = Optional.ofNullable(ingredient);

        this.originalIngredient.ifPresent(originalIngredient -> {
            this
                .name(originalIngredient.getName())
                .type(originalIngredient.getType());
        });
    }

    @Override
    public IngredientBuilderStub name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public IngredientBuilderStub type(IngredientType type) {
        this.type = type;

        return this;
    }

    @Override
    public IngredientStub build() {
        return this.originalIngredient
            .orElseGet(IngredientStub::new)
            .setName(this.name)
            .setType(this.type);
    }
}
