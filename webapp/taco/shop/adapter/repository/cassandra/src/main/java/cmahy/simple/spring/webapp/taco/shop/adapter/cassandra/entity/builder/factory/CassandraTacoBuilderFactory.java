package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder.CassandraTacoBuilder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraTacoProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactory;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

@Primary
@Named
public class CassandraTacoBuilderFactory implements TacoBuilderFactory<CassandraTacoProxy> {

    private final CassandraTacoProxyFactory tacoProxyFactory;

    public CassandraTacoBuilderFactory(CassandraTacoProxyFactoryProvider factoryProvider) {
        this.tacoProxyFactory = factoryProvider.resolve(CassandraTaco.class);
    }

    @Override
    public CassandraTacoBuilder create() {
        return new CassandraTacoBuilder(tacoProxyFactory);
    }

    @Override
    public CassandraTacoBuilder create(CassandraTacoProxy taco) {
        return new CassandraTacoBuilder(tacoProxyFactory, taco);
    }
}
