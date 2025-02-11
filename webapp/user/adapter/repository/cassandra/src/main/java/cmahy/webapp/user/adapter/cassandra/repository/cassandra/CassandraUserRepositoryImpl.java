package cmahy.webapp.user.adapter.cassandra.repository.cassandra;

import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.webapp.user.kernel.domain.id.RoleId;
import cmahy.webapp.user.kernel.domain.id.UserId;
import org.springframework.data.cassandra.repository.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CassandraUserRepositoryImpl extends CassandraRepository<CassandraUserImpl, UUID> {

    default Optional<CassandraUserImpl> findById(UserId userId) {
        return this.findById(userId.value());
    }

    default List<CassandraUserImpl> findByCassandraRoleId(RoleId roleId) {
        return findByCassandraRoleId(roleId.value());
    }

    @Query("select * from user where cassandraroleids contains ?0 allow filtering")
    List<CassandraUserImpl> findByCassandraRoleId(UUID roleId);

    @AllowFiltering
    Optional<CassandraUserImpl> findByUserName(String username);
}
