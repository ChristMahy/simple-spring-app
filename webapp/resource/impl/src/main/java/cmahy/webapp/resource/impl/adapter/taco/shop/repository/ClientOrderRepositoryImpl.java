package cmahy.webapp.resource.impl.adapter.taco.shop.repository;

import cmahy.webapp.resource.impl.application.taco.shop.repository.ClientOrderRepository;
import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientOrderRepositoryImpl extends ClientOrderRepository, JpaRepository<ClientOrder, Long> {
}
