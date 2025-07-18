package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;

public class CassandraRoleProxyFactory implements CassandraProxyFactory {

    private final UserLoaderProvider userLoaderProvider;
    private final CassandraUserProxyFactoryProvider factoryProvider;

    public CassandraRoleProxyFactory(
        UserLoaderProvider userLoaderProvider,
        CassandraUserProxyFactoryProvider factoryProvider
    ) {
        this.userLoaderProvider = userLoaderProvider;
        this.factoryProvider = factoryProvider;
    }

    public CassandraRoleProxy create(CassandraRole role) {
        return new CassandraRoleProxy(role, userLoaderProvider, factoryProvider);
    }
}
