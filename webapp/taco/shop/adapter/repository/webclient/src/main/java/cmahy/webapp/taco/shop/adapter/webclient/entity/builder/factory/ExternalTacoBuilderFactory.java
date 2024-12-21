package cmahy.webapp.taco.shop.adapter.webclient.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.webclient.entity.ExternalTaco;
import cmahy.webapp.taco.shop.adapter.webclient.entity.builder.ExternalTacoBuilder;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactory;
import jakarta.inject.Named;

@Named
public class ExternalTacoBuilderFactory implements TacoBuilderFactory<ExternalTaco> {

    @Override
    public ExternalTacoBuilder create() {
        return new ExternalTacoBuilder();
    }

    @Override
    public ExternalTacoBuilder create(ExternalTaco taco) {
        return new ExternalTacoBuilder(taco);
    }
}
