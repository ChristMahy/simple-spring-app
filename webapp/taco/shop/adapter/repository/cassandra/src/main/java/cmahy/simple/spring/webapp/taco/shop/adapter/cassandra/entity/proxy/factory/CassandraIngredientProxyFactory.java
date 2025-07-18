package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import jakarta.inject.Named;

public class CassandraIngredientProxyFactory implements CassandraProxyFactory {

    public CassandraIngredientProxy create(CassandraIngredient ingredient) {
        return new CassandraIngredientProxy(ingredient);
    }

}
