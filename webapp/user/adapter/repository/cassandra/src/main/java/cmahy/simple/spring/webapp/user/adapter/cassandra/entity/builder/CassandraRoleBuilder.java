package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.*;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RoleBuilder;

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
                .users(originalRole.getUsers())
                .rights(originalRole.getRights());
        });
    }

    private String name;
    private List<CassandraUserProxy> users;
    private Collection<CassandraRightProxy> rights;

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
    public <RIGHT extends Right> RoleBuilder<CassandraRoleProxy> rights(Collection<RIGHT> rights) {
        this.rights = (Collection<CassandraRightProxy>) rights;

        return this;
    }

    @Override
    public CassandraRoleProxy build() {
        return this.originalRole
            .orElseGet(() -> roleProxyFactory.create(new CassandraRole()))
            .setName(name)
            .setUsers(users)
            .setRights(rights);
    }
}
