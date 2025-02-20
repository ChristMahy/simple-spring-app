package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.UserSecurityLoader;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserSecurityProxy;
import jakarta.inject.Named;

@Named
public class CassandraUserSecurityProxyFactory {

    private final UserSecurityLoader userSecurityLoader;

    public CassandraUserSecurityProxyFactory(UserSecurityLoader userSecurityLoader) {
        this.userSecurityLoader = userSecurityLoader;
    }

    public CassandraUserSecurityProxy create(CassandraUserSecurityImpl userSecurity) {
        return new CassandraUserSecurityProxy(userSecurity, userSecurityLoader);
    }
}
