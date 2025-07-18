package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraRoleRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import jakarta.inject.Named;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class UserSecurityLoaderImpl implements UserSecurityLoader {

    private final CassandraRoleRepositoryImpl cassandraRoleRepository;

    public UserSecurityLoaderImpl(
        CassandraRoleRepositoryImpl cassandraRoleRepository
    ) {
        this.cassandraRoleRepository = cassandraRoleRepository;
    }

    @Override
    public Class<CassandraUserSecurityImpl> entityClass() {
        return CassandraUserSecurityImpl.class;
    }

    @Override
    public Collection<CassandraRole> loadRoles(Set<RoleId> roleIds) {
        return cassandraRoleRepository.findAllById(roleIds);
    }
}
