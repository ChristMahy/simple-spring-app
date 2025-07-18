package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserSecurityProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;

public class CassandraUserSecurityProxyFactory implements CassandraProxyFactory {

    private final UserLoaderProvider userLoaderProvider;
    private final CassandraUserProxyFactoryProvider factoryProvider;

    public CassandraUserSecurityProxyFactory(
        UserLoaderProvider userLoaderProvider,
        CassandraUserProxyFactoryProvider factoryProvider
    ) {
        this.userLoaderProvider = userLoaderProvider;
        this.factoryProvider = factoryProvider;
    }

    public CassandraUserSecurityProxy create(CassandraUserSecurityImpl userSecurity) {
        return new CassandraUserSecurityProxy(userSecurity, userLoaderProvider, factoryProvider);
    }
}
