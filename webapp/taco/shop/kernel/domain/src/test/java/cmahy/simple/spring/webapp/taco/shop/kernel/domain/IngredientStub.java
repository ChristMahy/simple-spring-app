package cmahy.simple.spring.webapp.taco.shop.kernel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

public class IngredientStub implements Ingredient {

    private UUID id;
    private String name;
    private IngredientType type;

    @Override
    public UUID getId() {
        return id;
    }

    public IngredientStub setId(UUID id) {
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
