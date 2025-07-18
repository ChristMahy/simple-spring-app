package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.*;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.RoleLoader;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRightProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;

import java.util.*;
import java.util.stream.Collectors;

public class CassandraRoleProxy implements Role {

    private final CassandraRole role;
    private List<CassandraUserProxy> users;
    private Collection<CassandraRightProxy> rights;
    private final UserLoaderProvider userLoaderProvider;
    private final CassandraUserProxyFactoryProvider factoryProvider;

    public CassandraRoleProxy(
        CassandraRole role,
        UserLoaderProvider userLoaderProvider,
        CassandraUserProxyFactoryProvider factoryProvider
    ) {
        this.role = role;
        this.userLoaderProvider = userLoaderProvider;
        this.factoryProvider = factoryProvider;
    }

    public CassandraRole unwrap() {
        return role;
    }

    @Override
    public UUID getId() {
        return role.getId();
    }

    @Override
    public String getName() {
        return role.getName();
    }

    public CassandraRoleProxy setName(String name) {
        role.setName(name);

        return this;
    }

    @Override
    public Collection<CassandraUserProxy> getUsers() {
        if (Objects.isNull(users)) {
            CassandraUserProxyFactory factory = factoryProvider.resolve(CassandraUserImpl.class);

            users = ((RoleLoader) userLoaderProvider.resolve(CassandraRole.class))
                .loadUsers(new RoleId(role.getId()))
                .stream()
                .map(factory::create)
                .collect(Collectors.toList());
        }

        return users;
    }

    public CassandraRoleProxy setUsers(List<CassandraUserProxy> users) {
        this.users = users;

        return this;
    }

    @Override
    public Collection<CassandraRightProxy> getRights() {
        if (Objects.isNull(rights)) {
            CassandraRightProxyFactory factory = factoryProvider.resolve(CassandraRight.class);

            rights = ((RoleLoader) userLoaderProvider.resolve(CassandraRole.class))
                .loadRights(new HashSet<>(role.getCassandraRightIds()))
                .stream()
                .map(factory::create)
                .collect(Collectors.toSet());
        }

        return rights;
    }

    public CassandraRoleProxy setRights(Collection<CassandraRightProxy> rights) {
        this.rights = Optional.ofNullable(rights).orElseGet(ArrayList::new);

        this.role.setCassandraRightIds(
            this.rights.stream()
                .map(Right::getId)
                .map(RightId::new)
                .toList()
        );

        return this;
    }

    @Override
    public String toString() {
        return role.toString();
    }
}
