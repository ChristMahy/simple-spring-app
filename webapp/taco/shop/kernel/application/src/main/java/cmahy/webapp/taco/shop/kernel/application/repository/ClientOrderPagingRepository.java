package cmahy.webapp.taco.shop.kernel.application.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.webapp.user.kernel.domain.User;

public interface ClientOrderPagingRepository {

    ClientOrderPage getByUser(User user, EntityPageable pageable);
}
