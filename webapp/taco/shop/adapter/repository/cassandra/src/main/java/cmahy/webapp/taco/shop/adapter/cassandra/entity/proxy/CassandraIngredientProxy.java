package cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;

import java.util.UUID;

public class CassandraIngredientProxy implements Ingredient {

    private final CassandraIngredient ingredient;

    public CassandraIngredientProxy(CassandraIngredient ingredient) {
        this.ingredient = ingredient;
    }

    public CassandraIngredient unwrap() {
        return ingredient;
    }

    @Override
    public UUID getId() {
        return ingredient.getId();
    }

    public CassandraIngredientProxy setId(UUID id) {
        this.ingredient.setId(id);
        return this;
    }

    @Override
    public String getName() {
        return ingredient.getName();
    }

    public CassandraIngredientProxy setName(String name) {
        this.ingredient.setName(name);
        return this;
    }

    @Override
    public IngredientType getType() {
        return ingredient.getType();
    }

    public CassandraIngredientProxy setType(IngredientType type) {
        this.ingredient.setType(type);
        return this;
    }
}
