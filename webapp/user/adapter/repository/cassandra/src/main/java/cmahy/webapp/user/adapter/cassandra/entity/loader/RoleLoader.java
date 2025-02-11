package cmahy.webapp.user.adapter.cassandra.entity.loader;

import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.webapp.user.kernel.domain.id.RoleId;

import java.util.List;

public interface RoleLoader {

    List<CassandraUserProxy> loadUsers(RoleId roleId);
}
