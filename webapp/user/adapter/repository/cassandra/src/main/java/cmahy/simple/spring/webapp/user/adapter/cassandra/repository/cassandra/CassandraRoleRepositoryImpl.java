package cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import org.springframework.data.cassandra.repository.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CassandraRoleRepositoryImpl extends CassandraRepository<CassandraRole, UUID> {

    @AllowFiltering
    Optional<CassandraRole> findByName(String name);

    default List<CassandraRole> findAllById(Set<RoleId> ids) {
        return findAllById(ids.stream().map(RoleId::value).toList());
    }

    @Query("select * from role where cassandrarightids contains ?0 allow filtering")
    Set<CassandraRole> findAllByRightId(UUID id);

    default Set<CassandraRole> findAllByRightId(RightId id) {
        return this.findAllByRightId(id.value());
    }
}
