package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.*;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.*;
import jakarta.inject.Named;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.util.Map;
import java.util.Optional;

@Named
@ConditionalOnMissingBean(CassandraUserProxyFactoryProvider.class)
public class CassandraUserProxyFactoryProviderImpl implements CassandraUserProxyFactoryProvider {

    private final Map<Class<?>, CassandraProxyFactory> factories;

    public CassandraUserProxyFactoryProviderImpl(
        UserLoaderProvider userLoaderProvider
    ) {
        this.factories = Map.of(
            CassandraRight.class, new CassandraRightProxyFactory(userLoaderProvider, this),
            CassandraRole.class, new CassandraRoleProxyFactory(userLoaderProvider, this),
            CassandraUserImpl.class, new CassandraUserProxyFactory(userLoaderProvider, this),
            CassandraUserSecurityImpl.class, new CassandraUserSecurityProxyFactory(userLoaderProvider, this)
        );
    }

    @Override
    public <T, F> F resolve(Class<T> entityClass) {
        return Optional.ofNullable(entityClass)
            .filter(factories::containsKey)
            .map(ec -> (F) factories.get(ec))
            .orElseThrow(() -> new IllegalArgumentException("Enity class not match"));
    }

}
