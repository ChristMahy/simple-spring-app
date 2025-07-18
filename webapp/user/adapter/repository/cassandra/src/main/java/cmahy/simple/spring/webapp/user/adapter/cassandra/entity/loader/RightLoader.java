package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;

import java.util.Collection;

public interface RightLoader extends Loader {

    Collection<CassandraRole> loadRoles(RightId rightId);

}
