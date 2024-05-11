package cmahy.webapp.resource.impl.adapter.taco.shop.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.resource.impl.application.taco.shop.repository.ClientOrderPagingRepository;
import cmahy.webapp.resource.impl.application.taco.shop.repository.ClientOrderRepository;
import cmahy.webapp.resource.impl.domain.taco.ClientOrder;
import cmahy.webapp.resource.impl.domain.taco.page.ClientOrderPage;
import cmahy.webapp.resource.impl.domain.user.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientOrderRepositoryImpl extends
    ClientOrderRepository,
    ClientOrderPagingRepository,
    JpaRepository<ClientOrder, Long> {

    Page<ClientOrder> findByUser(User user, Pageable pageable);

    @Override
    default ClientOrderPage getByUser(User user, EntityPageable pageable) {
        Page<ClientOrder> all = ClientOrderRepositoryImpl.this.findByUser(user, PageRequest.of(pageable.pageNumber().intValue(), pageable.pageSize()));

        return new ClientOrderPage(
            all.getContent(),
            all.getTotalElements()
        );
    }
}
