package cmahy.webapp.taco.shop.adapter.webclient.entity.builder;

import cmahy.webapp.taco.shop.adapter.webclient.entity.ExternalIngredient;
import cmahy.webapp.taco.shop.adapter.webclient.entity.ExternalTaco;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.builder.TacoBuilder;

import java.util.*;

public class ExternalTacoBuilder implements TacoBuilder<ExternalTaco> {

    private Optional<ExternalTaco> originalTaco = Optional.empty();

    private Date createdAt;

    private String name;

    private Collection<ExternalIngredient> ingredients;

    public ExternalTacoBuilder() {}

    public ExternalTacoBuilder(ExternalTaco tacoStub) {
        this.originalTaco = Optional.ofNullable(tacoStub);

        this.originalTaco.ifPresent(taco -> {
            this
                .name(taco.getName())
                .createdAt(taco.getCreatedAt())
                .ingredients(taco.getIngredients());
        });
    }

    @Override
    public ExternalTacoBuilder createdAt(Date createdAt) {
        this.createdAt = createdAt;

        return this;
    }

    @Override
    public ExternalTacoBuilder name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public <I extends Ingredient> ExternalTacoBuilder ingredients(Collection<I> ingredients) {
        this.ingredients = (List<ExternalIngredient>) ingredients;

        return this;
    }

    @Override
    public ExternalTaco build() {
        return this.originalTaco
            .orElseGet(ExternalTaco::new)
            .setName(this.name)
            .setIngredients(this.ingredients)
            .setCreatedAt(this.createdAt);
    }
}
