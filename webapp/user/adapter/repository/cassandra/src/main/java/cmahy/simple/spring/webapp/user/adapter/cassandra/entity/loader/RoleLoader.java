package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;

import java.util.List;

public interface RoleLoader {

    List<CassandraUserProxy> loadUsers(RoleId roleId);
}
