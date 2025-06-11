package cmahy.simple.spring.webapp.user.kernel.domain.builder.factory;

import cmahy.simple.spring.webapp.user.kernel.domain.RightStub;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RightBuilderStub;

public class RightBuilderFactoryStub implements RightBuilderFactory<RightStub> {

    @Override
    public RightBuilderStub create() {
        return new RightBuilderStub();
    }

    @Override
    public RightBuilderStub create(RightStub right) {
        return new RightBuilderStub(right);
    }

}
