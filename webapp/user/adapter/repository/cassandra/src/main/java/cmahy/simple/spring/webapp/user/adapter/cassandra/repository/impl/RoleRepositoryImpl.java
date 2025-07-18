package cmahy.simple.spring.webapp.user.adapter.cassandra.repository.impl;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraRoleRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import jakarta.inject.Named;

import java.util.*;

@Named
public class RoleRepositoryImpl implements RoleRepository<CassandraRoleProxy> {

    private final CassandraRoleRepositoryImpl cassandraRoleRepository;
    private final CassandraUserProxyFactoryProvider proxyFactoryResolver;

    public RoleRepositoryImpl(
        CassandraRoleRepositoryImpl cassandraRoleRepository,
        CassandraUserProxyFactoryProvider proxyFactoryResolver
    ) {
        this.cassandraRoleRepository = cassandraRoleRepository;
        this.proxyFactoryResolver = proxyFactoryResolver;
    }

    @Override
    public Optional<CassandraRoleProxy> findByName(String name) {
        return cassandraRoleRepository.findByName(name)
            .map(resolveFactory()::create);
    }

    @Override
    public CassandraRoleProxy save(CassandraRoleProxy role) {
        CassandraRole cassandraRole = role.unwrap();

        if (Objects.isNull(cassandraRole.getId())) {
            cassandraRole.setId(UUID.randomUUID());
        }

        return resolveFactory().create(
            cassandraRoleRepository.save(cassandraRole)
        );
    }

    private CassandraRoleProxyFactory resolveFactory() {
        return proxyFactoryResolver.resolve(CassandraRole.class);
    }
}
