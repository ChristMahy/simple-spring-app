package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.*;

import java.util.*;

public class TacoBuilderStub implements TacoBuilder<TacoStub> {

    private Optional<TacoStub> originalTaco = Optional.empty();

    private Date createdAt;

    private String name;

    private Collection<IngredientStub> ingredients;

    public TacoBuilderStub() {}

    public TacoBuilderStub(TacoStub tacoStub) {
        this.originalTaco = Optional.ofNullable(tacoStub);

        this.originalTaco.ifPresent(taco -> {
            this
                .name(taco.getName())
                .createdAt(taco.getCreatedAt())
                .ingredients(taco.getIngredients());
        });
    }

    @Override
    public TacoBuilderStub createdAt(Date createdAt) {
        this.createdAt = createdAt;

        return this;
    }

    @Override
    public TacoBuilderStub name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public <I extends Ingredient> TacoBuilderStub ingredients(Collection<I> ingredients) {
        this.ingredients = (List<IngredientStub>) ingredients;

        return this;
    }

    @Override
    public TacoStub build() {
        return this.originalTaco
            .orElseGet(TacoStub::new)
            .setName(this.name)
            .setIngredients(this.ingredients)
            .setCreatedAt(this.createdAt);
    }
}
