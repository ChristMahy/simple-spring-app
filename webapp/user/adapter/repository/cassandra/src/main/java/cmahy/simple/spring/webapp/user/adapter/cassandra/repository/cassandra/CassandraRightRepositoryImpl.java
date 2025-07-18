package cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRightProxy;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public interface CassandraRightRepositoryImpl extends CassandraRepository<CassandraRight, UUID> {

    @AllowFiltering
    Set<CassandraRight> findAllByIdIn(Set<UUID> ids);

    default Collection<CassandraRight> findByRightIds(Set<RightId> rightIds) {
        return this.findAllByIdIn(rightIds.stream().map(RightId::value).collect(Collectors.toSet()));
    }

    @AllowFiltering
    Optional<CassandraRight> findByName(String name);

}
