package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.RightLoader;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;

import java.util.*;
import java.util.stream.Collectors;

public class CassandraRightProxy implements Right {

    private final CassandraRight right;
    private Collection<CassandraRoleProxy> roles;
    private final UserLoaderProvider userLoaderProvider;
    private final CassandraUserProxyFactoryProvider factoryProvider;

    public CassandraRightProxy(
        CassandraRight right,
        UserLoaderProvider userLoaderProvider,
        CassandraUserProxyFactoryProvider factoryProvider
    ) {
        this.right = right;
        this.userLoaderProvider = userLoaderProvider;
        this.factoryProvider = factoryProvider;
    }

    public CassandraRight unwrap() {
        return right;
    }

    @Override
    public UUID getId() {
        return right.getId();
    }

    @Override
    public String getName() {
        return right.getName();
    }

    public CassandraRightProxy setName(String name) {
        right.setName(name);

        return this;
    }

    @Override
    public Collection<CassandraRoleProxy> getRoles() {
        if (Objects.isNull(roles)) {
            CassandraRoleProxyFactory factory = factoryProvider.resolve(CassandraRole.class);

            roles = ((RightLoader) userLoaderProvider.resolve(CassandraRight.class))
                .loadRoles(new RightId(getId())).stream()
                .map(factory::create)
                .collect(Collectors.toSet());
        }

        return roles;
    }

    public CassandraRightProxy setRoles(Collection<CassandraRoleProxy> roles) {
        this.roles = roles;

        return this;
    }

    @Override
    public String toString() {
        return right.toString();
    }
}
