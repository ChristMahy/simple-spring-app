package cmahy.webapp.taco.shop.adapter.jpa.entity.domain;

import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.Taco;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

@Entity
@Table(name = "taco")
public class JpaTaco implements Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    protected Date createdAt;

    protected String name;

    @ManyToMany
    protected Collection<JpaIngredient> ingredients = new ArrayList<>();

    @Override
    public UUID getId() {
        return id;
    }

    public JpaTaco setId(UUID id) {
        this.id = id;

        return this;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    public JpaTaco setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;

        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public JpaTaco setName(String name) {
        this.name = name;

        return this;
    }

    @Override
    public Collection<JpaIngredient> getIngredients() {
        return ingredients;
    }

    public JpaTaco setIngredients(Collection<JpaIngredient> ingredients) {
        this.ingredients = Objects.isNull(ingredients) ? null : new ArrayList<>(ingredients);

        return this;
    }

    @PrePersist
    public void prePersist() {
        if (Objects.isNull(this.createdAt)) {
            this.createdAt = new Date();
        }
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

        this.ingredients.add((JpaIngredient) ingredient);
    }
}
