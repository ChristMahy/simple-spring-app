package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder.CassandraRoleBuilder;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RoleBuilderFactory;
import jakarta.inject.Named;

@Named
public class CassandraRoleBuilderFactory implements RoleBuilderFactory<CassandraRoleProxy> {

    private final CassandraRoleProxyFactory cassandraRoleProxyFactory;

    public CassandraRoleBuilderFactory(CassandraUserProxyFactoryProvider factoryProvider) {
        this.cassandraRoleProxyFactory = factoryProvider.resolve(CassandraRole.class);
    }

    @Override
    public CassandraRoleBuilder create() {
        return new CassandraRoleBuilder(cassandraRoleProxyFactory);
    }

    @Override
    public CassandraRoleBuilder create(CassandraRoleProxy role) {
        return new CassandraRoleBuilder(cassandraRoleProxyFactory, role);
    }
}
