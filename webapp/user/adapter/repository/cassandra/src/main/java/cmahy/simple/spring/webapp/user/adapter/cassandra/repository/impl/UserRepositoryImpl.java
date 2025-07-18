package cmahy.simple.spring.webapp.user.adapter.cassandra.repository.impl;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class UserRepositoryImpl implements UserRepository<CassandraUserProxy> {

    private final CassandraUserRepositoryImpl cassandraUserRepository;
    private final CassandraUserProxyFactoryProvider proxyFactoryResolver;

    public UserRepositoryImpl(
        CassandraUserRepositoryImpl cassandraUserRepository,
        CassandraUserProxyFactoryProvider proxyFactoryResolver
    ) {
        this.cassandraUserRepository = cassandraUserRepository;
        this.proxyFactoryResolver = proxyFactoryResolver;
    }

    @Override
    public Optional<CassandraUserProxy> findById(UserId id) {
        return cassandraUserRepository.findById(id)
            .map(resolveFactory()::create);
    }

    @Override
    public Optional<CassandraUserProxy> findByUserName(String username) {
        return cassandraUserRepository.findByUserName(username)
            .map(resolveFactory()::create);
    }

    private CassandraUserProxyFactory resolveFactory() {
        return proxyFactoryResolver.resolve(CassandraUserImpl.class);
    }
}
