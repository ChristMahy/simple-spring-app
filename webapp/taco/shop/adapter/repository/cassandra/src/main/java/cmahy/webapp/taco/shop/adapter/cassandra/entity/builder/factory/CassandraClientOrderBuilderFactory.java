package cmahy.webapp.taco.shop.adapter.cassandra.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.builder.CassandraClientOrderBuilder;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraClientOrderProxyFactory;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactory;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

@Primary
@Named
public class CassandraClientOrderBuilderFactory implements ClientOrderBuilderFactory<CassandraClientOrderProxy> {

    private final CassandraClientOrderProxyFactory clientOrderProxyFactory;

    public CassandraClientOrderBuilderFactory(CassandraClientOrderProxyFactory clientOrderProxyFactory) {
        this.clientOrderProxyFactory = clientOrderProxyFactory;
    }

    @Override
    public CassandraClientOrderBuilder create() {
        return new CassandraClientOrderBuilder(clientOrderProxyFactory);
    }

    @Override
    public CassandraClientOrderBuilder create(CassandraClientOrderProxy clientOrder) {
        return new CassandraClientOrderBuilder(clientOrderProxyFactory, clientOrder);
    }
}
