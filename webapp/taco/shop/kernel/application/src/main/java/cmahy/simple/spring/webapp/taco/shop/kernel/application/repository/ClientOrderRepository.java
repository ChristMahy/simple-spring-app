package cmahy.simple.spring.webapp.taco.shop.kernel.application.repository;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;

public interface ClientOrderRepository<CO extends ClientOrder> {

    CO save(CO clientOrder);
}
