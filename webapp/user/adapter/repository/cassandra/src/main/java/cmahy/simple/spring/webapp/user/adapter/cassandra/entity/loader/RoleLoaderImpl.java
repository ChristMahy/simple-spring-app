package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import jakarta.inject.Named;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.stream.Collectors;

@Named
public class RoleLoaderImpl implements RoleLoader {

    private final CassandraUserRepositoryImpl cassandraUserRepository;
    private final CassandraUserProxyFactory cassandraUserProxyFactory;

    public RoleLoaderImpl(
        CassandraUserRepositoryImpl cassandraUserRepository,
        @Lazy CassandraUserProxyFactory cassandraUserProxyFactory
    ) {
        this.cassandraUserRepository = cassandraUserRepository;
        this.cassandraUserProxyFactory = cassandraUserProxyFactory;
    }

    @Override
    public List<CassandraUserProxy> loadUsers(RoleId roleId) {
        return cassandraUserRepository
            .findByCassandraRoleId(roleId).stream()
            .map(cassandraUserProxyFactory::create)
            .collect(Collectors.toList());
    }
}
