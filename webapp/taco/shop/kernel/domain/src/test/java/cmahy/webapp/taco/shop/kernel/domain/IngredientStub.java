package cmahy.webapp.taco.shop.kernel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class IngredientStub implements Ingredient {

    private String id;
    private String name;
    private IngredientType type;

    @Override
    public String getId() {
        return id;
    }

    public IngredientStub setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public IngredientStub setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IngredientType getType() {
        return type;
    }

    public IngredientStub setType(IngredientType type) {
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
