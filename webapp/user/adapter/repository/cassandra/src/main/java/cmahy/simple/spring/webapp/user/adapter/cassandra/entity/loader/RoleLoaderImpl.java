package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.*;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraRightRepositoryImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import jakarta.inject.Named;

import java.util.Collection;
import java.util.Set;

@Named
public class RoleLoaderImpl implements RoleLoader {

    private final CassandraUserRepositoryImpl cassandraUserRepository;
    private final CassandraRightRepositoryImpl cassandraRightRepository;

    public RoleLoaderImpl(
        CassandraUserRepositoryImpl cassandraUserRepository,
        CassandraRightRepositoryImpl cassandraRightRepository
    ) {
        this.cassandraUserRepository = cassandraUserRepository;
        this.cassandraRightRepository = cassandraRightRepository;
    }

    @Override
    public Class<CassandraRole> entityClass() {
        return CassandraRole.class;
    }

    @Override
    public Collection<CassandraUserImpl> loadUsers(RoleId roleId) {
        return cassandraUserRepository.findByCassandraRoleId(roleId);
    }

    @Override
    public Collection<CassandraRight> loadRights(Set<RightId> rightIds) {
        return cassandraRightRepository.findByRightIds(rightIds);
    }

}
