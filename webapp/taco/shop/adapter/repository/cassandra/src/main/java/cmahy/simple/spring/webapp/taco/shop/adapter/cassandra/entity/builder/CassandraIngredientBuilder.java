package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraIngredientProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.IngredientBuilder;

import java.util.Optional;

public class CassandraIngredientBuilder implements IngredientBuilder<CassandraIngredientProxy> {

    private final CassandraIngredientProxyFactory ingredientProxyFactory;
    private Optional<CassandraIngredientProxy> originalIngredient = Optional.empty();

    private String name;
    private IngredientType type;


    public CassandraIngredientBuilder(
        CassandraIngredientProxyFactory ingredientProxyFactory
    ) {
        this.ingredientProxyFactory = ingredientProxyFactory;
    }

    public CassandraIngredientBuilder(
        CassandraIngredientProxyFactory ingredientProxyFactory,
        CassandraIngredientProxy originalIngredient
    ) {
        this(ingredientProxyFactory);

        this.originalIngredient = Optional.ofNullable(originalIngredient);

        this.originalIngredient.ifPresent(ingredient -> {
            this
                .name(ingredient.getName())
                .type(ingredient.getType());
        });
    }

    @Override
    public CassandraIngredientBuilder name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public CassandraIngredientBuilder type(IngredientType type) {
        this.type = type;

        return this;
    }

    @Override
    public CassandraIngredientProxy build() {
        return this.originalIngredient
            .orElseGet(() -> ingredientProxyFactory.create(new CassandraIngredient()))
            .setName(this.name)
            .setType(this.type);
    }
}
