package cmahy.webapp.user.kernel.domain;

import java.util.*;

public class RoleStub implements Role {

    private Long id;

    private String name;

    private Collection<UserStub> users;

    @Override
    public Long getId() {
        return id;
    }

    public RoleStub setId(Long id) {
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

    public Collection<UserStub> getUsers() {
        return users;
    }

    public RoleStub setUsers(Collection<UserStub> users) {
        this.users = Objects.isNull(users) ? null : new ArrayList<>(users);
        return this;
    }
}
