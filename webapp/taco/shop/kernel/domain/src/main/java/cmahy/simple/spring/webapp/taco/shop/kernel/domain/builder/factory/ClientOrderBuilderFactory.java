package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilder;

public interface ClientOrderBuilderFactory<CO extends ClientOrder> {

    ClientOrderBuilder<CO> create();

    ClientOrderBuilder<CO> create(CO clientOrder);
}
