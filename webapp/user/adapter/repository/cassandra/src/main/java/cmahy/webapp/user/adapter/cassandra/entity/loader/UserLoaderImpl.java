package cmahy.webapp.user.adapter.cassandra.entity.loader;

import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.webapp.user.adapter.cassandra.repository.cassandra.CassandraRoleRepositoryImpl;
import cmahy.webapp.user.kernel.domain.id.RoleId;
import jakarta.inject.Named;
import org.springframework.context.annotation.Lazy;

import java.util.Set;
import java.util.stream.Collectors;

@Named
public class UserLoaderImpl implements UserLoader {

    private final CassandraRoleRepositoryImpl cassandraRoleRepository;
    private final CassandraRoleProxyFactory cassandraRoleProxyFactory;

    public UserLoaderImpl(
        CassandraRoleRepositoryImpl cassandraRoleRepository,
        @Lazy CassandraRoleProxyFactory cassandraRoleProxyFactory
    ) {
        this.cassandraRoleRepository = cassandraRoleRepository;
        this.cassandraRoleProxyFactory = cassandraRoleProxyFactory;
    }

    @Override
    public Set<CassandraRoleProxy> loadRoles(Set<RoleId> roleIds) {
        return cassandraRoleRepository.findAllById(roleIds).stream()
            .map(cassandraRoleProxyFactory::create)
            .collect(Collectors.toSet());
    }
}
