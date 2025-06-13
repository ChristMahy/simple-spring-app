package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRight;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRole;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RightBuilder;

import java.util.Collection;
import java.util.Optional;

public class JpaRightBuilder implements RightBuilder<JpaRight> {

    private Optional<JpaRight> originalRight = Optional.empty();

    public JpaRightBuilder() {}

    public JpaRightBuilder(JpaRight right) {

        this.originalRight = Optional.ofNullable(right);

        this.originalRight.ifPresent(originalRight -> {
            this.name(originalRight.getName())
                .roles(originalRight.getRoles());
        });

    }

    private String name;
    private Collection<JpaRole> roles;

    @Override
    public JpaRightBuilder name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public <ROLE extends Role> JpaRightBuilder roles(Collection<ROLE> roles) {
        this.roles = (Collection<JpaRole>) roles;

        return this;
    }

    @Override
    public JpaRight build() {
        return this.originalRight.orElseGet(JpaRight::new)
            .setName(name)
            .setRoles(roles);
    }
}
