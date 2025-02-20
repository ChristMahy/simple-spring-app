package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.RoleLoader;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;

import java.util.*;

public class CassandraRoleProxy implements Role {

    private final CassandraRole role;
    private List<CassandraUserProxy> users;
    private final RoleLoader roleLoader;

    public CassandraRoleProxy(
        CassandraRole role,
        RoleLoader roleLoader
    ) {
        this.role = role;
        this.roleLoader = roleLoader;
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
            users = roleLoader.loadUsers(new RoleId(role.getId()));
        }

        return users;
    }

    public CassandraRoleProxy setUsers(List<CassandraUserProxy> users) {
        this.users = users;
        return this;
    }

    @Override
    public String toString() {
        return role.toString();
    }
}
