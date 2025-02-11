package cmahy.webapp.user.adapter.cassandra.entity.builder.factory;

import cmahy.webapp.user.adapter.cassandra.entity.builder.CassandraUserSecurityBuilder;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraUserSecurityProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserSecurityProxyFactory;
import cmahy.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
import jakarta.inject.Named;

@Named
public class CassandraUserSecurityBuilderFactory implements UserSecurityBuilderFactory<CassandraUserSecurityProxy> {

    private final CassandraUserSecurityProxyFactory userSecurityProxyFactory;

    public CassandraUserSecurityBuilderFactory(CassandraUserSecurityProxyFactory userSecurityProxyFactory) {
        this.userSecurityProxyFactory = userSecurityProxyFactory;
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
