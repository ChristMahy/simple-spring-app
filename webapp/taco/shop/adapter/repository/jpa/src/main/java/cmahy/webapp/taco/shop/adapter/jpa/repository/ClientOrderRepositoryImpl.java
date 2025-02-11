package cmahy.webapp.taco.shop.adapter.jpa.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.adapter.jpa.entity.domain.JpaClientOrder;
import cmahy.webapp.taco.shop.kernel.application.repository.ClientOrderPagingRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.ClientOrderRepository;
import cmahy.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.webapp.user.adapter.jpa.entity.domain.JpaUser;
import cmahy.webapp.user.kernel.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Primary
@Repository
public interface ClientOrderRepositoryImpl extends
    ClientOrderRepository<JpaClientOrder>,
    ClientOrderPagingRepository<JpaClientOrder>,
    JpaRepository<JpaClientOrder, UUID> {

    Page<JpaClientOrder> findByUser(JpaUser user, Pageable pageable);

    @Override
    default ClientOrderPage<JpaClientOrder> getByUser(User user, EntityPageable pageable) {
        Page<JpaClientOrder> all = ClientOrderRepositoryImpl.this.findByUser((JpaUser) user, PageRequest.of(pageable.pageNumber(), pageable.pageSize()));

        return new ClientOrderPage<>(
            all.getContent(),
            all.getTotalElements()
        );
    }
}
