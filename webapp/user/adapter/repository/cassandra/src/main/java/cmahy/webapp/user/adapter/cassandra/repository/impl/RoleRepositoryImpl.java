package cmahy.webapp.user.adapter.cassandra.repository.impl;

import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.webapp.user.adapter.cassandra.repository.cassandra.CassandraRoleRepositoryImpl;
import cmahy.webapp.user.kernel.application.repository.RoleRepository;
import jakarta.inject.Named;

import java.util.*;

@Named
public class RoleRepositoryImpl implements RoleRepository<CassandraRoleProxy> {

    private final CassandraRoleRepositoryImpl cassandraRoleRepository;
    private final CassandraRoleProxyFactory cassandraRoleProxyFactory;

    public RoleRepositoryImpl(
        CassandraRoleRepositoryImpl cassandraRoleRepository,
        CassandraRoleProxyFactory cassandraRoleProxyFactory
    ) {
        this.cassandraRoleRepository = cassandraRoleRepository;
        this.cassandraRoleProxyFactory = cassandraRoleProxyFactory;
    }

    @Override
    public Optional<CassandraRoleProxy> findByName(String name) {
        return cassandraRoleRepository.findByName(name)
            .map(cassandraRoleProxyFactory::create);
    }

    @Override
    public CassandraRoleProxy save(CassandraRoleProxy role) {
        CassandraRole cassandraRole = role.unwrap();

        if (Objects.isNull(cassandraRole.getId())) {
            cassandraRole.setId(UUID.randomUUID());
        }

        return cassandraRoleProxyFactory.create(
            cassandraRoleRepository.save(cassandraRole)
        );
    }
}
