package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.*;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraRoleRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import jakarta.inject.Named;

import java.util.Collection;
import java.util.Set;

@Named
public class UserLoaderImpl implements UserLoader {

    private final CassandraRoleRepositoryImpl cassandraRoleRepository;

    public UserLoaderImpl(
        CassandraRoleRepositoryImpl cassandraRoleRepository
    ) {
        this.cassandraRoleRepository = cassandraRoleRepository;
    }

    @Override
    public Class<CassandraUserImpl> entityClass() {
        return CassandraUserImpl.class;
    }

    @Override
    public Collection<CassandraRole> loadRoles(Set<RoleId> roleIds) {
        return cassandraRoleRepository.findAllById(roleIds);
    }
}
