package cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.factory;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.loader.ClientOrderLoader;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import jakarta.inject.Named;

@Named
public class CassandraClientOrderProxyFactory {

    private final ClientOrderLoader clientOrderLoader;

    public CassandraClientOrderProxyFactory(ClientOrderLoader clientOrderLoader) {
        this.clientOrderLoader = clientOrderLoader;
    }

    public CassandraClientOrderProxy create(CassandraClientOrder clientOrder) {
        return new CassandraClientOrderProxy(clientOrder, clientOrderLoader);
    }
}
