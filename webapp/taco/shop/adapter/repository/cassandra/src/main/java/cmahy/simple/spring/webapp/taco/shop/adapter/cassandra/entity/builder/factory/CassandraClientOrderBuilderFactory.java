package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder.CassandraClientOrderBuilder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraClientOrderProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactory;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

@Primary
@Named
public class CassandraClientOrderBuilderFactory implements ClientOrderBuilderFactory<CassandraClientOrderProxy> {

    private final CassandraClientOrderProxyFactory clientOrderProxyFactory;

    public CassandraClientOrderBuilderFactory(CassandraTacoProxyFactoryProvider factoryProvider) {
        this.clientOrderProxyFactory = factoryProvider.resolve(CassandraClientOrder.class);
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
