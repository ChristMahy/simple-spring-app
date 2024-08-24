package cmahy.webapp.taco.shop.adapter.cassandra.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.application.repository.ClientOrderPagingRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.ClientOrderRepository;
import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.webapp.user.kernel.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface ClientOrderRepositoryImpl extends
    ClientOrderRepository,
    ClientOrderPagingRepository,
    CassandraRepository<ClientOrder, Long> {

    Page<ClientOrder> findByUser(User user, Pageable pageable);

    @Override
    default ClientOrderPage getByUser(User user, EntityPageable pageable) {
        Page<ClientOrder> all = this.findByUser(
            user,
            PageRequest.of(pageable.pageNumber(), pageable.pageSize())
        );

        return new ClientOrderPage(
            all.getContent(),
            all.getTotalElements()
        );
    }
}
