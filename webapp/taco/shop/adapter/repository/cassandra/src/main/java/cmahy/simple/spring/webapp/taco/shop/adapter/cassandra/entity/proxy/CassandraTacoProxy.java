package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.TacoLoader;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;

import java.util.*;
import java.util.stream.Collectors;

public class CassandraTacoProxy implements Taco {

    private final CassandraTaco taco;
    private List<CassandraIngredientProxy> ingredients;
    private final TacoLoader tacoLoader;

    public CassandraTacoProxy(
        CassandraTaco taco,
        TacoLoader tacoLoader
    ) {
        this.taco = taco;
        this.tacoLoader = tacoLoader;
    }

    public CassandraTaco unwrap() {
        return taco;
    }

    @Override
    public UUID getId() {
        return taco.getId();
    }

    @Override
    public Date getCreatedAt() {
        return taco.getCreatedAt();
    }

    public CassandraTacoProxy setCreatedAt(Date createdAt) {
        this.taco.setCreatedAt(createdAt);
        return this;
    }

    @Override
    public String getName() {
        return taco.getName();
    }

    public CassandraTacoProxy setName(String name) {
        this.taco.setName(name);
        return this;
    }

    @Override
    public Collection<CassandraIngredientProxy> getIngredients() {
        if (ingredients == null) {
            ingredients = tacoLoader.loadIngredients(taco.getIngredientIds());
        }

        return ingredients;
    }

    public CassandraTacoProxy setIngredients(List<CassandraIngredientProxy> ingredients) {
        this.ingredients = Optional.ofNullable(ingredients).orElseGet(ArrayList::new);

        // TODO: Multi NPE on this.taco ????
        this.taco.setIngredientIds(
            this.ingredients.stream()
                .map(Ingredient::getId)
                .map(IngredientId::new)
                .collect(Collectors.toSet())
        );

        return this;
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        if (Objects.isNull(ingredient)) {
            return;
        }

        if (Objects.isNull(ingredients)) {
            ingredients = new ArrayList<>();
        }

        this.ingredients.add((CassandraIngredientProxy) ingredient);
        this.taco.addIngredientId(new IngredientId(ingredient.getId()));
    }
}
