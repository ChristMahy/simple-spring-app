package cmahy.simple.spring.webapp.user.kernel.domain;

import java.util.*;

public class RoleStub implements Role {

    private UUID id;

    private String name;

    private Collection<UserStub> users;

    private Collection<RightStub> rights;

    @Override
    public UUID getId() {
        return id;
    }

    public RoleStub setId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public RoleStub setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Collection<UserStub> getUsers() {
        return users;
    }

    public RoleStub setUsers(Collection<UserStub> users) {
        this.users = Objects.isNull(users) ? null : new ArrayList<>(users);
        return this;
    }

    @Override
    public Collection<RightStub> getRights() {
        return rights;
    }

    public RoleStub setRights(Collection<RightStub> rights) {
        this.rights = rights;

        return this;
    }
}
