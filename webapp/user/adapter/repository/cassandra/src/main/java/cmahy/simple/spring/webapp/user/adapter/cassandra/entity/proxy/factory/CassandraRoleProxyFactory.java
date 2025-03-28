package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.RoleLoader;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import jakarta.inject.Named;

@Named
public class CassandraRoleProxyFactory {

    private final RoleLoader roleLoader;

    public CassandraRoleProxyFactory(RoleLoader roleLoader) {
        this.roleLoader = roleLoader;
    }

    public CassandraRoleProxy create(CassandraRole role) {
        return new CassandraRoleProxy(role, roleLoader);
    }
}
