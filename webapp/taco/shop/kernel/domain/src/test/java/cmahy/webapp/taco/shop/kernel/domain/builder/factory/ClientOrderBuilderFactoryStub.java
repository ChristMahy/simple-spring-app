package cmahy.webapp.taco.shop.kernel.domain.builder.factory;

import cmahy.webapp.taco.shop.kernel.domain.ClientOrderStub;
import cmahy.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilderStub;

public class ClientOrderBuilderFactoryStub implements ClientOrderBuilderFactory<ClientOrderStub> {

    @Override
    public ClientOrderBuilderStub create() {
        return new ClientOrderBuilderStub();
    }

    @Override
    public ClientOrderBuilderStub create(ClientOrderStub clientOrder) {
        return new ClientOrderBuilderStub(clientOrder);
    }
}
