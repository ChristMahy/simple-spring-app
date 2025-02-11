package cmahy.webapp.taco.shop.adapter.webclient.entity.domain;

import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.Taco;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

public class ExternalTaco implements Taco {

    private UUID id;

    private Date createdAt;

    private String name;

    private Collection<ExternalIngredient> ingredients;

    @Override
    public UUID getId() {
        return id;
    }

    public ExternalTaco setId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    public ExternalTaco setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public ExternalTaco setName(String name) {
        this.name = name;
        return this;
    }

    public Collection<ExternalIngredient> getIngredients() {
        return ingredients;
    }

    public ExternalTaco setIngredients(Collection<ExternalIngredient> ingredients) {
        this.ingredients = Objects.isNull(ingredients) ? null : new ArrayList<>(ingredients);

        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", getId())
            .append("createdAt", getCreatedAt())
            .append("name", getName())
            .toString();
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        if (Objects.isNull(ingredient)) {
            return;
        }

        if (Objects.isNull(this.ingredients)) {
            this.ingredients = new ArrayList<>();
        }

        this.ingredients.add((ExternalIngredient) ingredient);
    }
}
