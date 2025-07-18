package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.TacoLoader;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.provider.TacoLoaderProvider;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraIngredientProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;

import java.util.*;
import java.util.stream.Collectors;

public class CassandraTacoProxy implements Taco {

    private final CassandraTaco taco;
    private List<CassandraIngredientProxy> ingredients;

    private final TacoLoaderProvider tacoLoaderProvider;
    private final CassandraTacoProxyFactoryProvider factoryProvider;

    public CassandraTacoProxy(
        CassandraTaco taco,
        TacoLoaderProvider tacoLoaderProvider,
        CassandraTacoProxyFactoryProvider factoryProvider
    ) {
        this.taco = taco;

        this.tacoLoaderProvider = tacoLoaderProvider;
        this.factoryProvider = factoryProvider;
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
            CassandraIngredientProxyFactory factory = factoryProvider.resolve(CassandraIngredient.class);

            ingredients = ((TacoLoader) tacoLoaderProvider.resolve(CassandraTaco.class))
                .loadIngredients(taco.getIngredientIds()).stream()
                .map(factory::create)
                .toList();
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
