package cmahy.webapp.taco.shop.adapter.cassandra.entity.domain;

import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("ingredient")
public class CassandraIngredient {

    @PrimaryKey
    protected UUID id;
    protected String name;
    protected IngredientType type;

    public UUID getId() {
        return id;
    }

    public CassandraIngredient setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CassandraIngredient setName(String name) {
        this.name = name;
        return this;
    }

    public IngredientType getType() {
        return type;
    }

    public CassandraIngredient setType(IngredientType type) {
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
