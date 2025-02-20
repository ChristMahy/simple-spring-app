package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder.CassandraIngredientBuilder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraIngredientProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.IngredientBuilderFactory;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

@Primary
@Named
public class CassandraIngredientBuilderFactory implements IngredientBuilderFactory<CassandraIngredientProxy> {

    private final CassandraIngredientProxyFactory ingredientProxyFactory;

    public CassandraIngredientBuilderFactory(CassandraIngredientProxyFactory ingredientProxyFactory) {
        this.ingredientProxyFactory = ingredientProxyFactory;
    }

    @Override
    public CassandraIngredientBuilder create() {
        return new CassandraIngredientBuilder(ingredientProxyFactory);
    }

    @Override
    public CassandraIngredientBuilder create(CassandraIngredientProxy ingredient) {
        return new CassandraIngredientBuilder(ingredientProxyFactory, ingredient);
    }
}
