package cmahy.webapp.taco.shop.kernel.application.repository;

import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;

public interface ClientOrderRepository {

    ClientOrder save(ClientOrder clientOrder);
}
