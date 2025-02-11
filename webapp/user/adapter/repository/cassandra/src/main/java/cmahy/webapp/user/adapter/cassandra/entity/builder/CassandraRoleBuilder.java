package cmahy.webapp.user.adapter.cassandra.entity.builder;

import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.domain.builder.RoleBuilder;

import java.util.*;

public class CassandraRoleBuilder implements RoleBuilder<CassandraRoleProxy> {

    private final CassandraRoleProxyFactory roleProxyFactory;
    private Optional<CassandraRoleProxy> originalRole = Optional.empty();

    public CassandraRoleBuilder(CassandraRoleProxyFactory roleProxyFactory) {
        this.roleProxyFactory = roleProxyFactory;
    }

    public CassandraRoleBuilder(CassandraRoleProxyFactory roleProxyFactory, CassandraRoleProxy role) {
        this(roleProxyFactory);

        this.originalRole = Optional.ofNullable(role);

        this.originalRole.ifPresent(originalRole -> {
            this.name(originalRole.getName())
                .users(originalRole.getUsers());
        });
    }

    private String name;
    private List<CassandraUserProxy> users;

    @Override
    public CassandraRoleBuilder name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public <U extends User> CassandraRoleBuilder users(Collection<U> users) {
        this.users = (List<CassandraUserProxy>) users;

        return this;
    }

    @Override
    public CassandraRoleProxy build() {
        return this.originalRole
            .orElseGet(() -> roleProxyFactory.create(new CassandraRole()))
            .setName(name)
            .setUsers(users);
    }
}
