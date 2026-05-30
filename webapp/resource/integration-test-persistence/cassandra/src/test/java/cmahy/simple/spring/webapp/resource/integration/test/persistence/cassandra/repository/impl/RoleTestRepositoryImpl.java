package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.impl;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.api.repository.RoleTestRepository;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.cassandra.CassandraRoleTestRepository;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import jakarta.inject.Named;

import java.util.Objects;
import java.util.UUID;

@Named
public class RoleTestRepositoryImpl implements RoleTestRepository<CassandraRoleProxy> {

    private final CassandraRoleTestRepository cassandraRoleTestRepository;
    private final CassandraUserProxyFactoryProvider proxyFactoryResolver;


    public RoleTestRepositoryImpl(
        CassandraRoleTestRepository cassandraRoleTestRepository,
        CassandraUserProxyFactoryProvider proxyFactoryResolver
    ) {
        this.cassandraRoleTestRepository = cassandraRoleTestRepository;
        this.proxyFactoryResolver = proxyFactoryResolver;
    }

    @Override
    public CassandraRoleProxy save(CassandraRoleProxy role) {

        CassandraRole cassandraRole = role.unwrap();

        if (Objects.isNull(cassandraRole.getId())) {
            cassandraRole.setId(UUID.randomUUID());
        }

        return resolveFactory().create(
            cassandraRoleTestRepository.save(cassandraRole)
        );

    }

    private CassandraRoleProxyFactory resolveFactory() {
        return proxyFactoryResolver.resolve(CassandraRole.class);
    }

}
