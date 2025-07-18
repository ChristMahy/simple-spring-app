package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;

import java.util.Collection;
import java.util.Set;

public interface RoleLoader extends Loader {

    Collection<CassandraUserImpl> loadUsers(RoleId roleId);

    Collection<CassandraRight> loadRights(Set<RightId> rightIds);

}
