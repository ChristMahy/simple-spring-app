package cmahy.webapp.taco.shop.adapter.jpa.entity.builder;

import cmahy.webapp.taco.shop.adapter.jpa.entity.domain.JpaIngredient;
import cmahy.webapp.taco.shop.adapter.jpa.entity.domain.JpaTaco;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.builder.TacoBuilder;

import java.util.*;

public class JpaTacoBuilder implements TacoBuilder<JpaTaco> {

    private Optional<JpaTaco> originalTaco = Optional.empty();

    private Date createdAt;

    private String name;

    private Collection<JpaIngredient> ingredients;

    public JpaTacoBuilder() { }

    public JpaTacoBuilder(JpaTaco jpaTaco) {
        originalTaco = Optional.ofNullable(jpaTaco);

        originalTaco.ifPresent(taco -> {
            this
                .name(taco.getName())
                .createdAt(taco.getCreatedAt())
                .ingredients(taco.getIngredients());
        });
    }

    @Override
    public TacoBuilder<JpaTaco> createdAt(Date createdAt) {
        this.createdAt = createdAt;

        return this;
    }

    @Override
    public TacoBuilder<JpaTaco> name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public <I extends Ingredient> TacoBuilder<JpaTaco> ingredients(Collection<I> ingredients) {
        this.ingredients = (List<JpaIngredient>) ingredients;

        return this;
    }

    @Override
    public JpaTaco build() {
        return this.originalTaco
            .orElseGet(JpaTaco::new)
            .setName(this.name)
            .setIngredients(this.ingredients)
            .setCreatedAt(this.createdAt);
    }
}
