package cmahy.webapp.user.adapter.cassandra.repository.cassandra;

import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.webapp.user.kernel.domain.id.RoleId;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CassandraRoleRepositoryImpl extends CassandraRepository<CassandraRole, UUID> {

    @AllowFiltering
    Optional<CassandraRole> findByName(String name);

    default List<CassandraRole> findAllById(Set<RoleId> ids) {
        return findAllById(ids.stream().map(RoleId::value).toList());
    }
}
