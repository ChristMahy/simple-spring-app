package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.*;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
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
                .users(originalRole.getUsers())
                .rights(originalRole.getRights());
        });
    }

    private String name;
    private List<JpaUser> users;
    private Collection<JpaRight> rights;

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
    public <RIGHT extends Right> JpaRoleBuilder rights(Collection<RIGHT> rights) {
        this.rights = (Collection<JpaRight>) rights;

        return this;
    }

    @Override
    public JpaRole build() {
        return this.originalRole.orElseGet(JpaRole::new)
            .setName(name)
            .setUsers(users)
            .setRights(rights);
    }
}
