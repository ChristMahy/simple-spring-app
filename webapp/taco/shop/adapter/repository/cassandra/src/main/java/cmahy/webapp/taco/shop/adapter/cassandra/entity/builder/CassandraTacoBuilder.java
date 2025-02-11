package cmahy.webapp.taco.shop.adapter.cassandra.entity.builder;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraTacoProxyFactory;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.builder.TacoBuilder;

import java.util.*;

public class CassandraTacoBuilder implements TacoBuilder<CassandraTacoProxy> {

    private final CassandraTacoProxyFactory tacoProxyFactory;
    private Optional<CassandraTacoProxy> originalTaco = Optional.empty();

    private String name;

    private List<CassandraIngredientProxy> ingredients;

    public CassandraTacoBuilder(CassandraTacoProxyFactory tacoProxyFactory) {
        this.tacoProxyFactory = tacoProxyFactory;
    }

    public CassandraTacoBuilder(CassandraTacoProxyFactory tacoProxyFactory, CassandraTacoProxy originalTaco) {
        this(tacoProxyFactory);

        this.originalTaco = Optional.ofNullable(originalTaco);

        this.originalTaco.ifPresent(taco -> {
            this
                .name(taco.getName())
                .ingredients(taco.getIngredients());
        });
    }

    @Override
    public CassandraTacoBuilder createdAt(Date createdAt) {
        return this;
    }

    @Override
    public CassandraTacoBuilder name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public <I extends Ingredient> CassandraTacoBuilder ingredients(Collection<I> ingredients) {
        this.ingredients = (List<CassandraIngredientProxy>) ingredients;

        return this;
    }

    @Override
    public CassandraTacoProxy build() {
        return this.originalTaco
            .orElseGet(() -> tacoProxyFactory.create(
                new CassandraTaco().setCreatedAt(new Date())
            ))
            .setName(name)
            .setIngredients(ingredients);
    }
}
