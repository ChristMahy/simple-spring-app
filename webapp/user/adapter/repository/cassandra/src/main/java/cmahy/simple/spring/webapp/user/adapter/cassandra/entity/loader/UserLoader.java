package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;

import java.util.Set;

public interface UserLoader {

    Set<CassandraRoleProxy> loadRoles(Set<RoleId> roleIds);
}
