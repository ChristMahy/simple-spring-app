package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.CassandraRightBuilder;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRightProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRightProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RightBuilderFactory;
import jakarta.inject.Named;

@Named
public class CassandraRightBuilderFactory implements RightBuilderFactory<CassandraRightProxy> {

    private final CassandraRightProxyFactory cassandraRightProxyFactory;

    public CassandraRightBuilderFactory(CassandraUserProxyFactoryProvider factoryProvider) {
        this.cassandraRightProxyFactory = factoryProvider.resolve(CassandraRight.class);
    }

    @Override
    public CassandraRightBuilder create() {
        return new CassandraRightBuilder(cassandraRightProxyFactory);
    }

    @Override
    public CassandraRightBuilder create(CassandraRightProxy right) {
        return new CassandraRightBuilder(cassandraRightProxyFactory, right);
    }

}
