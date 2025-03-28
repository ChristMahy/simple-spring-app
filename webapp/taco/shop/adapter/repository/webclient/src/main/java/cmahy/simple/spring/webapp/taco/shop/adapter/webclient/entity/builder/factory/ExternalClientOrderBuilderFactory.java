package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.domain.ExternalClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.builder.ExternalClientOrderBuilder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactory;
import jakarta.inject.Named;

@Named
public class ExternalClientOrderBuilderFactory implements ClientOrderBuilderFactory<ExternalClientOrder> {

    @Override
    public ExternalClientOrderBuilder create() {
        return new ExternalClientOrderBuilder();
    }

    @Override
    public ExternalClientOrderBuilder create(ExternalClientOrder clientOrder) {
        return new ExternalClientOrderBuilder(clientOrder);
    }
}
