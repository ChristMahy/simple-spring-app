package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRole;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaUser;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RoleBuilder;

import java.util.*;

public class JpaRoleBuilder implements RoleBuilder<JpaRole> {
    
    private Optional<JpaRole> originalRole = Optional.empty();

    public JpaRoleBuilder() {}

    public JpaRoleBuilder(JpaRole role) {
        this.originalRole = Optional.ofNullable(role);

        this.originalRole.ifPresent(originalRole -> {
            this.name(originalRole.getName())
                .users(originalRole.getUsers());
        });
    }

    private String name;
    private List<JpaUser> users;

    @Override
    public JpaRoleBuilder name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public <U extends User> JpaRoleBuilder users(Collection<U> users) {
        this.users = (List<JpaUser>) users;

        return this;
    }

    @Override
    public JpaRole build() {
        return this.originalRole.orElseGet(JpaRole::new)
            .setName(name)
            .setUsers(users);
    }
}
