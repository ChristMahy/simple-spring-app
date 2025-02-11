package cmahy.webapp.taco.shop.adapter.cassandra.repository.impl;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraClientOrderProxyFactory;
import cmahy.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraClientOrderRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.ClientOrderPagingRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.ClientOrderRepository;
import cmahy.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.domain.id.UserId;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.*;

@Primary
@Named
public class ClientOrderRepositoryImpl implements
    ClientOrderRepository<CassandraClientOrderProxy>,
    ClientOrderPagingRepository<CassandraClientOrderProxy> {

    private final CassandraClientOrderRepository clientOrderRepository;
    private final CassandraClientOrderProxyFactory clientOrderProxyFactory;

    public ClientOrderRepositoryImpl(
        CassandraClientOrderRepository clientOrderRepository,
        CassandraClientOrderProxyFactory clientOrderProxyFactory
    ) {
        this.clientOrderRepository = clientOrderRepository;
        this.clientOrderProxyFactory = clientOrderProxyFactory;
    }

    @Override
    public ClientOrderPage<CassandraClientOrderProxy> getByUser(User user, EntityPageable pageable) {
        Slice<CassandraClientOrderProxy> clientOrders = clientOrderRepository
            .findByUserId(
                new UserId(user.getId()),
                PageRequest.of(pageable.pageNumber(), pageable.pageSize())
            )
            .map(clientOrderProxyFactory::create);

        return new ClientOrderPage<>(
            clientOrders.getContent(),
            Integer.valueOf(clientOrders.getNumberOfElements()).longValue()
        );
    }

    @Override
    public CassandraClientOrderProxy save(CassandraClientOrderProxy clientOrder) {
        CassandraClientOrder clientOrderUnwrapped = clientOrder.unwrap();

        if (Objects.isNull(clientOrderUnwrapped.getId())) {
            clientOrderUnwrapped.setId(UUID.randomUUID());
        }

        if (Objects.isNull(clientOrderUnwrapped.getPlacedAt())) {
            clientOrderUnwrapped.setPlacedAt(new Date());
        }

        return clientOrderProxyFactory.create(
            clientOrderRepository.save(clientOrderUnwrapped)
        );
    }
}
