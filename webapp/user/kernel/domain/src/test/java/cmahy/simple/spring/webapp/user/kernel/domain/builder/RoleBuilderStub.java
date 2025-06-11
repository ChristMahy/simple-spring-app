package cmahy.simple.spring.webapp.user.kernel.domain.builder;

import cmahy.simple.spring.webapp.user.kernel.domain.*;

import java.util.*;

public class RoleBuilderStub implements RoleBuilder<RoleStub> {

    private Optional<RoleStub> originalRole = Optional.empty();

    private String name;
    private List<UserStub> users;

    public RoleBuilderStub() {}

    public RoleBuilderStub(RoleStub role) {
        this.originalRole = Optional.ofNullable(role);

        this.originalRole.ifPresent(originalRole -> {
            this.name(originalRole.getName())
                .users(originalRole.getUsers());
        });
    }

    @Override
    public RoleBuilderStub name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public <U extends User> RoleBuilderStub users(Collection<U> users) {
        this.users = (List<UserStub>) users;

        return this;
    }

    @Override
    public <RIGHT extends Right> RoleBuilder<RoleStub> rights(Collection<RIGHT> rights) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public RoleStub build() {
        return this.originalRole.orElseGet(RoleStub::new)
            .setName(name)
            .setUsers(users);
    }
}
