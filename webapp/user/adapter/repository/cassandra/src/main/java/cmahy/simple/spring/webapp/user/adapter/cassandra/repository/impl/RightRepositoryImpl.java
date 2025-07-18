package cmahy.simple.spring.webapp.user.adapter.cassandra.repository.impl;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRightProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRightProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraRightRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import jakarta.inject.Named;

import java.util.*;

@Named
public class RightRepositoryImpl implements RightRepository<CassandraRightProxy> {

    private final CassandraRightRepositoryImpl cassandraRightRepository;
    private final CassandraUserProxyFactoryProvider proxyFactoryResolver;

    public RightRepositoryImpl(
        CassandraRightRepositoryImpl cassandraRightRepository,
        CassandraUserProxyFactoryProvider proxyFactoryResolver
    ) {
        this.cassandraRightRepository = cassandraRightRepository;
        this.proxyFactoryResolver = proxyFactoryResolver;
    }


    @Override
    public Optional<CassandraRightProxy> findByName(String name) {
        return this.cassandraRightRepository.findByName(name)
            .map(resolveFactory()::create);
    }

    @Override
    public CassandraRightProxy save(CassandraRightProxy right) {
        CassandraRight cassandraRight = right.unwrap();

        if (Objects.isNull(cassandraRight.getId())) {
            cassandraRight.setId(UUID.randomUUID());
        }

        return resolveFactory().create(
            cassandraRightRepository.save(cassandraRight)
        );
    }

    private CassandraRightProxyFactory resolveFactory() {
        return proxyFactoryResolver.resolve(CassandraRight.class);
    }
}
