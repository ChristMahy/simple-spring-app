package cmahy.simple.spring.webapp.user.adapter.cassandra.repository.impl;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserSecurityProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserSecurityProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraUserSecurityRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import jakarta.inject.Named;

import java.util.*;

@Named
public class UserSecurityRepositoryImpl implements UserSecurityRepository<CassandraUserSecurityProxy> {


    private final CassandraUserSecurityRepositoryImpl userSecurityRepository;
    private final CassandraUserProxyFactoryProvider proxyFactoryResolver;

    public UserSecurityRepositoryImpl(
        CassandraUserSecurityRepositoryImpl userSecurityRepository,
        CassandraUserProxyFactoryProvider proxyFactoryResolver
    ) {
        this.userSecurityRepository = userSecurityRepository;
        this.proxyFactoryResolver = proxyFactoryResolver;
    }

    @Override
    public Optional<CassandraUserSecurityProxy> findByUserNameAndAuthProvider(String username, AuthProvider authProvider) {
        return userSecurityRepository.findByUserNameAndAuthProvider(username, authProvider)
            .map(resolveFactory()::create);
    }

    @Override
    public CassandraUserSecurityProxy save(CassandraUserSecurityProxy userSecurity) {
        CassandraUserSecurityImpl userSecurityUnwrapped = userSecurity.unwrap();

        if (Objects.isNull(userSecurityUnwrapped.getId())) {
            userSecurityUnwrapped = userSecurityUnwrapped.setId(UUID.randomUUID());
        }

        return resolveFactory().create(
            userSecurityRepository.save(userSecurityUnwrapped)
        );
    }

    private CassandraUserSecurityProxyFactory resolveFactory() {
        return proxyFactoryResolver.resolve(CassandraUserSecurityImpl.class);
    }
}
