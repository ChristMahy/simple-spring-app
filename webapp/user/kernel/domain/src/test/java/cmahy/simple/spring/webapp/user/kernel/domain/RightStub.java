package cmahy.simple.spring.webapp.user.kernel.domain;

import java.util.Collection;
import java.util.UUID;

public class RightStub implements Right {

    private UUID id;

    private String name;

    private Collection<RoleStub> roles;

    @Override
    public UUID getId() {
        return id;
    }

    public RightStub setId(UUID id) {
        this.id = id;

        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public RightStub setName(String name) {
        this.name = name;

        return this;
    }

    @Override
    public Collection<RoleStub> getRoles() {
        return roles;
    }

    public RightStub setRoles(Collection<RoleStub> roles) {
        this.roles = roles;

        return this;
    }
}
