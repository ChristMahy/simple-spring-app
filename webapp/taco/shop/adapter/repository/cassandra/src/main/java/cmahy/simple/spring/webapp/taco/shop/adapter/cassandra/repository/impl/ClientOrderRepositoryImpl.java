package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.impl;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraClientOrderProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraClientOrderRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.ClientOrderPagingRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.ClientOrderRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.ClientOrderPage;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
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
    private final CassandraTacoProxyFactoryProvider factoryProvider;

    public ClientOrderRepositoryImpl(
        CassandraClientOrderRepository clientOrderRepository,
        CassandraTacoProxyFactoryProvider factoryProvider
    ) {
        this.clientOrderRepository = clientOrderRepository;
        this.factoryProvider = factoryProvider;
    }

    @Override
    public ClientOrderPage<CassandraClientOrderProxy> getByUser(User user, EntityPageable pageable) {
        CassandraClientOrderProxyFactory factory = factoryProvider.resolve(CassandraClientOrder.class);

        Slice<CassandraClientOrderProxy> clientOrders = clientOrderRepository
            .findByUserId(
                new UserId(user.getId()),
                PageRequest.of(pageable.pageNumber(), pageable.pageSize())
            )
            .map(factory::create);

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

        CassandraClientOrderProxyFactory factory = factoryProvider.resolve(CassandraClientOrder.class);

        return factory.create(
            clientOrderRepository.save(clientOrderUnwrapped)
        );
    }
}
