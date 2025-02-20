package cmahy.simple.spring.webapp.taco.shop.adapter.jpa.repository;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.domain.JpaClientOrder;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.ClientOrderPagingRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.ClientOrderRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaUser;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
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
