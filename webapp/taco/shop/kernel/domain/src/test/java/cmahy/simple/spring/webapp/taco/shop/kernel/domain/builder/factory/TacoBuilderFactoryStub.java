package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.TacoStub;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.TacoBuilderStub;

public class TacoBuilderFactoryStub implements TacoBuilderFactory<TacoStub> {

    @Override
    public TacoBuilderStub create() {
        return new TacoBuilderStub();
    }

    @Override
    public TacoBuilderStub create(TacoStub taco) {
        return new TacoBuilderStub(taco);
    }
}
