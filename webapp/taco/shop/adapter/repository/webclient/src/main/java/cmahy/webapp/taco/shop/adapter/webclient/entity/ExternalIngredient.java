package cmahy.webapp.taco.shop.adapter.webclient.entity;

import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ExternalIngredient implements Ingredient {

    private String id;
    private String name;
    private IngredientType type;

    @Override
    public String getId() {
        return id;
    }

    public ExternalIngredient setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public ExternalIngredient setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IngredientType getType() {
        return type;
    }

    public ExternalIngredient setType(IngredientType type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("type", getType())
            .toString();
    }
}
