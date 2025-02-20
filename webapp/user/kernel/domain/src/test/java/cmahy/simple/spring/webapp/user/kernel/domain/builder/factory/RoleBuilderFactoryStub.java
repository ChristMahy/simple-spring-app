package cmahy.simple.spring.webapp.user.kernel.domain.builder.factory;

import cmahy.simple.spring.webapp.user.kernel.domain.RoleStub;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RoleBuilderStub;

public class RoleBuilderFactoryStub implements RoleBuilderFactory<RoleStub> {

    @Override
    public RoleBuilderStub create() {
        return new RoleBuilderStub();
    }

    @Override
    public RoleBuilderStub create(RoleStub role) {
        return new RoleBuilderStub(role);
    }
}
