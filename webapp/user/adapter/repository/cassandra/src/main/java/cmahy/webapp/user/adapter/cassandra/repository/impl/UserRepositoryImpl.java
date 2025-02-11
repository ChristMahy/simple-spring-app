package cmahy.webapp.user.adapter.cassandra.repository.impl;

import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserRepositoryImpl;
import cmahy.webapp.user.kernel.application.repository.UserRepository;
import cmahy.webapp.user.kernel.domain.id.UserId;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class UserRepositoryImpl implements UserRepository<CassandraUserProxy> {

    private final CassandraUserRepositoryImpl cassandraUserRepository;
    private final CassandraUserProxyFactory cassandraUserProxyFactory;

    public UserRepositoryImpl(
        CassandraUserRepositoryImpl cassandraUserRepository,
        CassandraUserProxyFactory cassandraUserProxyFactory
    ) {
        this.cassandraUserRepository = cassandraUserRepository;
        this.cassandraUserProxyFactory = cassandraUserProxyFactory;
    }

    @Override
    public Optional<CassandraUserProxy> findById(UserId id) {
        return cassandraUserRepository.findById(id)
            .map(cassandraUserProxyFactory::create);
    }

    @Override
    public Optional<CassandraUserProxy> findByUserName(String username) {
        return cassandraUserRepository.findByUserName(username)
            .map(cassandraUserProxyFactory::create);
    }
}
