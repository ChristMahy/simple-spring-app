package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.*;

@Table("taco")
public class CassandraTaco {

    @PrimaryKey
    protected UUID id;
    protected Date createdAt;

    protected String name;

    @CassandraType(type = CassandraType.Name.SET, typeArguments = CassandraType.Name.TEXT)
    protected Set<IngredientId> ingredientIds;

    public UUID getId() {
        return id;
    }

    public CassandraTaco setId(UUID id) {
        this.id = id;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public CassandraTaco setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getName() {
        return name;
    }

    public CassandraTaco setName(String name) {
        this.name = name;
        return this;
    }

    public Set<IngredientId> getIngredientIds() {
        return ingredientIds;
    }

    public CassandraTaco setIngredientIds(Set<IngredientId> ingredients) {
        this.ingredientIds = ingredients;
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

    public void addIngredientId(IngredientId ingredientId) {
        if (Objects.isNull(this.ingredientIds)) {
            this.ingredientIds = new HashSet<>();
        }

        this.ingredientIds.add(ingredientId);
    }
}
