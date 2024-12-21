package cmahy.webapp.taco.shop.adapter.jpa.entity;

import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "ingredient")
public class JpaIngredient implements Ingredient {

    @Id
    protected String id;
    protected String name;
    protected IngredientType type;

    @Override
    public String getId() {
        return id;
    }

    public JpaIngredient setId(String id) {
        this.id = id;

        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public JpaIngredient setName(String name) {
        this.name = name;

        return this;
    }

    @Override
    public IngredientType getType() {
        return type;
    }

    public JpaIngredient setType(IngredientType type) {
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
