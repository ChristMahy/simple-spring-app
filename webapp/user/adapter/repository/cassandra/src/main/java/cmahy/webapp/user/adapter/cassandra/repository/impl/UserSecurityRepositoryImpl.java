package cmahy.webapp.user.adapter.cassandra.repository.impl;

import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraUserSecurityProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserSecurityProxyFactory;
import cmahy.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserSecurityRepositoryImpl;
import cmahy.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.webapp.user.kernel.domain.AuthProvider;
import jakarta.inject.Named;

import java.util.*;

@Named
public class UserSecurityRepositoryImpl implements UserSecurityRepository<CassandraUserSecurityProxy> {


    private final CassandraUserSecurityRepositoryImpl userSecurityRepository;
    private final CassandraUserSecurityProxyFactory userSecurityProxyFactory;

    public UserSecurityRepositoryImpl(
        CassandraUserSecurityRepositoryImpl userSecurityRepository,
        CassandraUserSecurityProxyFactory userSecurityProxyFactory
    ) {
        this.userSecurityRepository = userSecurityRepository;
        this.userSecurityProxyFactory = userSecurityProxyFactory;
    }

    @Override
    public Optional<CassandraUserSecurityProxy> findByUserNameAndAuthProvider(String username, AuthProvider authProvider) {
        return userSecurityRepository.findByUserNameAndAuthProvider(username, authProvider)
            .map(userSecurityProxyFactory::create);
    }

    @Override
    public CassandraUserSecurityProxy save(CassandraUserSecurityProxy userSecurity) {
        CassandraUserSecurityImpl userSecurityUnwrapped = userSecurity.unwrap();

        if (Objects.isNull(userSecurityUnwrapped.getId())) {
            userSecurityUnwrapped = userSecurityUnwrapped.setId(UUID.randomUUID());
        }

        return userSecurityProxyFactory.create(
            userSecurityRepository.save(userSecurityUnwrapped)
        );
    }
}
