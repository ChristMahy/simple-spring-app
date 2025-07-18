package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;

import java.util.Collection;
import java.util.Set;

public interface UserLoader extends Loader {

    Collection<CassandraRole> loadRoles(Set<RoleId> roleIds);

}
