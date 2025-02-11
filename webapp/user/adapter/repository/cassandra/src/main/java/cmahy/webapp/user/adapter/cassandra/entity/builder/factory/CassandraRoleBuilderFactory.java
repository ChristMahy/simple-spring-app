package cmahy.webapp.user.adapter.cassandra.entity.builder.factory;

import cmahy.webapp.user.adapter.cassandra.entity.builder.CassandraRoleBuilder;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.webapp.user.kernel.domain.builder.factory.RoleBuilderFactory;
import jakarta.inject.Named;

@Named
public class CassandraRoleBuilderFactory implements RoleBuilderFactory<CassandraRoleProxy> {

    private final CassandraRoleProxyFactory cassandraRoleProxyFactory;

    public CassandraRoleBuilderFactory(CassandraRoleProxyFactory cassandraRoleProxyFactory) {
        this.cassandraRoleProxyFactory = cassandraRoleProxyFactory;
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
