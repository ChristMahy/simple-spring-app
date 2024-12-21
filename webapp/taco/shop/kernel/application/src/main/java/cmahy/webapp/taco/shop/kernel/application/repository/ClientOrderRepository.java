package cmahy.webapp.taco.shop.kernel.application.repository;

import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;

public interface ClientOrderRepository<CO extends ClientOrder> {

    CO save(CO clientOrder);
}
