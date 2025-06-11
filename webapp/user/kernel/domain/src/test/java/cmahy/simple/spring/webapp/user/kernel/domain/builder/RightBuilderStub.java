package cmahy.simple.spring.webapp.user.kernel.domain.builder;

import cmahy.simple.spring.webapp.user.kernel.domain.*;

import java.util.Collection;
import java.util.Optional;

public class RightBuilderStub implements RightBuilder<RightStub> {

    private Optional<RightStub> originalRight = Optional.empty();

    private String name;
    private Collection<RoleStub> roles;

    public RightBuilderStub() {}

    public RightBuilderStub(RightStub right) {
        this.originalRight = Optional.ofNullable(right);

        this.originalRight.ifPresent(originalRight -> {
            this.name(originalRight.getName())
                .roles(originalRight.getRoles());
        });
    }

    @Override
    public RightBuilder<RightStub> name(String name) {
        this.name = name;

        return this;
    }

    @Override
    public <ROLE extends Role> RightBuilder<RightStub> roles(Collection<ROLE> roles) {
        this.roles = (Collection<RoleStub>) roles;

        return this;
    }

    @Override
    public RightStub build() {
        return this.originalRight.orElseGet(RightStub::new)
            .setName(this.name)
            .setRoles(this.roles);
    }

}
