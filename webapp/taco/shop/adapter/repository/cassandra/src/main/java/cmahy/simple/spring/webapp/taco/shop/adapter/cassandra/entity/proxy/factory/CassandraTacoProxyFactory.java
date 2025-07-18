package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.provider.TacoLoaderProvider;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;

public class CassandraTacoProxyFactory implements CassandraProxyFactory {

    private final TacoLoaderProvider tacoLoaderProvider;
    private final CassandraTacoProxyFactoryProvider factoryProvider;

    public CassandraTacoProxyFactory(
        TacoLoaderProvider tacoLoaderProvider,
        CassandraTacoProxyFactoryProvider factoryProvider
    ) {
        this.tacoLoaderProvider = tacoLoaderProvider;
        this.factoryProvider = factoryProvider;
    }

    public CassandraTacoProxy create(CassandraTaco taco) {
        return new CassandraTacoProxy(taco, tacoLoaderProvider, factoryProvider);
    }
}
