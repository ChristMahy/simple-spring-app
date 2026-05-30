package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.impl;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.api.repository.RightTestRepository;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.cassandra.CassandraRightTestRepository;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraRightProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRightProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import jakarta.inject.Named;

import java.util.Objects;
import java.util.UUID;

@Named
public class RightTestRepositoryImpl implements RightTestRepository<CassandraRightProxy> {

    private final CassandraRightTestRepository cassandraRightTestRepository;
    private final CassandraUserProxyFactoryProvider proxyFactoryProvider;

    public RightTestRepositoryImpl(
        CassandraRightTestRepository cassandraRightTestRepository,
        CassandraUserProxyFactoryProvider proxyFactoryProvider
    ) {
        this.cassandraRightTestRepository = cassandraRightTestRepository;
        this.proxyFactoryProvider = proxyFactoryProvider;
    }

    @Override
    public CassandraRightProxy save(CassandraRightProxy right) {

        CassandraRight cassandraRight = right.unwrap();

        if (Objects.isNull(cassandraRight.getId())) {
            cassandraRight.setId(UUID.randomUUID());
        }

        return resolveFactory().create(
            cassandraRightTestRepository.save(cassandraRight)
        );

    }

    private CassandraRightProxyFactory resolveFactory() {
        return proxyFactoryProvider.resolve(CassandraRight.class);
    }

}
