package cmahy.webapp.user.adapter.cassandra.entity.loader;

import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.webapp.user.kernel.domain.id.RoleId;

import java.util.Set;

public interface UserSecurityLoader {

    Set<CassandraRoleProxy> loadRoles(Set<RoleId> roleIds);
}
