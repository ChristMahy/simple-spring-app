package cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.factory;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.loader.TacoLoader;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import jakarta.inject.Named;

@Named
public class CassandraTacoProxyFactory {

    private final TacoLoader tacoLoader;

    public CassandraTacoProxyFactory(TacoLoader tacoLoader) {
        this.tacoLoader = tacoLoader;
    }

    public CassandraTacoProxy create(CassandraTaco taco) {
        return new CassandraTacoProxy(taco, tacoLoader);
    }
}
