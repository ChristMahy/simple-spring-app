package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.CassandraUserSecurityBuilder;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserSecurityProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserSecurityProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
import jakarta.inject.Named;

@Named
public class CassandraUserSecurityBuilderFactory implements UserSecurityBuilderFactory<CassandraUserSecurityProxy> {

    private final CassandraUserSecurityProxyFactory userSecurityProxyFactory;

    public CassandraUserSecurityBuilderFactory(CassandraUserProxyFactoryProvider factoryProvider) {
        this.userSecurityProxyFactory = factoryProvider.resolve(CassandraUserSecurityImpl.class);
    }

    @Override
    public CassandraUserSecurityBuilder create() {
        return new CassandraUserSecurityBuilder(userSecurityProxyFactory);
    }

    @Override
    public CassandraUserSecurityBuilder create(CassandraUserSecurityProxy userSecurity) {
        return new CassandraUserSecurityBuilder(userSecurityProxyFactory, userSecurity);
    }
}
