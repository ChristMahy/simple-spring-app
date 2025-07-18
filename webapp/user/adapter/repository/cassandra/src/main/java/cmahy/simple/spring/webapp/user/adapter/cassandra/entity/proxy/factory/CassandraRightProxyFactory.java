package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRightProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;

public class CassandraRightProxyFactory implements CassandraProxyFactory {

    private final UserLoaderProvider userLoaderProvider;
    private final CassandraUserProxyFactoryProvider factoryProvider;

    public CassandraRightProxyFactory(
        UserLoaderProvider userLoaderProvider,
        CassandraUserProxyFactoryProvider factoryProvider
    ) {
        this.userLoaderProvider = userLoaderProvider;
        this.factoryProvider = factoryProvider;
    }

    public CassandraRightProxy create(CassandraRight right) {
        return new CassandraRightProxy(right, userLoaderProvider, factoryProvider);
    }

}
