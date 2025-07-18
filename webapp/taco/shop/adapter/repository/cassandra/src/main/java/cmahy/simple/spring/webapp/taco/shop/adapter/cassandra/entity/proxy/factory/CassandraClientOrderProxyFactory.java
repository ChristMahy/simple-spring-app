package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.provider.TacoLoaderProvider;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;

public class CassandraClientOrderProxyFactory implements CassandraProxyFactory {

    private final TacoLoaderProvider tacoLoaderProvider;
    private final CassandraTacoProxyFactoryProvider factoryProvider;

    public CassandraClientOrderProxyFactory(
        TacoLoaderProvider tacoLoaderProvider,
        CassandraTacoProxyFactoryProvider factoryProvider
    ) {
        this.tacoLoaderProvider = tacoLoaderProvider;
        this.factoryProvider = factoryProvider;
    }


    public CassandraClientOrderProxy create(CassandraClientOrder clientOrder) {
        return new CassandraClientOrderProxy(clientOrder, tacoLoaderProvider, factoryProvider);
    }
}
