package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.impl;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.application.repository.UserSecurityTestRepository;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.cassandra.CassandraUserSecurityTestRepository;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserSecurityProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserSecurityProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import jakarta.inject.Named;

import java.util.Objects;
import java.util.UUID;

@Named
public class UserSecurityTestRepositoryImpl implements UserSecurityTestRepository<CassandraUserSecurityProxy> {

    private final CassandraUserSecurityTestRepository cassandraUserSecurityTestRepository;
    private final CassandraUserProxyFactoryProvider proxyFactoryResolver;


    public UserSecurityTestRepositoryImpl(
        CassandraUserSecurityTestRepository cassandraUserSecurityTestRepository,
        CassandraUserProxyFactoryProvider proxyFactoryResolver
    ) {
        this.cassandraUserSecurityTestRepository = cassandraUserSecurityTestRepository;
        this.proxyFactoryResolver = proxyFactoryResolver;
    }

    @Override
    public CassandraUserSecurityProxy save(CassandraUserSecurityProxy user) {

        CassandraUserSecurityImpl unwrapped = user.unwrap();

        if (Objects.isNull(unwrapped.getId())) {
            unwrapped.setId(UUID.randomUUID());
        }

        CassandraUserSecurityProxyFactory proxyFactory = proxyFactoryResolver.resolve(CassandraUserSecurityImpl.class);

        return proxyFactory.create(
            cassandraUserSecurityTestRepository.save(unwrapped)
        );

    }

}
