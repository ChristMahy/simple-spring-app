package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.TacoBuilder;

public interface TacoBuilderFactory<T extends Taco> {

    TacoBuilder<T> create();

    TacoBuilder<T> create(T taco);
}
