package cmahy.simple.spring.webapp.taco.shop.kernel.application.repository;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.simple.spring.webapp.user.kernel.domain.User;

public interface ClientOrderPagingRepository<CO extends ClientOrder> {

    <U extends User> ClientOrderPage<CO> getByUser(U user, EntityPageable pageable);
}
