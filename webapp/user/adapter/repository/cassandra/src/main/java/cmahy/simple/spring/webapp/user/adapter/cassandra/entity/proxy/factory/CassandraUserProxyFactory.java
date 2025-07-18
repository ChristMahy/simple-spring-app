package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;

public class CassandraUserProxyFactory implements CassandraProxyFactory {

    private final UserLoaderProvider userLoaderProvider;
    private final CassandraUserProxyFactoryProvider factoryProvider;

    public CassandraUserProxyFactory(
        UserLoaderProvider userLoaderProvider,
        CassandraUserProxyFactoryProvider factoryProvider
    ) {
        this.userLoaderProvider = userLoaderProvider;
        this.factoryProvider = factoryProvider;
    }

    public CassandraUserProxy create(CassandraUserImpl user) {
        return new CassandraUserProxy(user, userLoaderProvider, factoryProvider);
    }
}
