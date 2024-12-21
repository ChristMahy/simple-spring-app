package cmahy.webapp.taco.shop.kernel.application.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.webapp.user.kernel.domain.User;

public interface ClientOrderPagingRepository<CO extends ClientOrder> {

    <U extends User> ClientOrderPage<CO> getByUser(U user, EntityPageable pageable);
}
