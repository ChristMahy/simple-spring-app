package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraClientOrderProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraClientOrderProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilder;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.kernel.domain.User;

import java.util.*;

public class CassandraClientOrderBuilder implements ClientOrderBuilder<CassandraClientOrderProxy> {

    private final CassandraClientOrderProxyFactory clientOrderProxyFactory;
    private Optional<CassandraClientOrderProxy> originalClientOrder = Optional.empty();

    private CassandraUserProxy user;

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;

    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<CassandraTacoProxy> tacos;

    public CassandraClientOrderBuilder(CassandraClientOrderProxyFactory clientOrderProxyFactory) {
        this.clientOrderProxyFactory = clientOrderProxyFactory;
    }

    public CassandraClientOrderBuilder(
        CassandraClientOrderProxyFactory clientOrderProxyFactory,
        CassandraClientOrderProxy originalClientOrder
    ) {
        this.clientOrderProxyFactory = clientOrderProxyFactory;
        this.originalClientOrder = Optional.ofNullable(originalClientOrder);

        this.originalClientOrder.ifPresent(clientOrder -> {
            this
                .user(clientOrder.getUser())
                .deliveryName(clientOrder.getDeliveryName())
                .deliveryStreet(clientOrder.getDeliveryStreet())
                .deliveryCity(clientOrder.getDeliveryCity())
                .deliveryState(clientOrder.getDeliveryState())
                .deliveryZip(clientOrder.getDeliveryZip())
                .ccNumber(clientOrder.getCcNumber())
                .ccExpiration(clientOrder.getCcExpiration())
                .ccCVV(clientOrder.getCcCVV())
                .tacos(clientOrder.getTacos());
        });
    }

    @Override
    public CassandraClientOrderBuilder user(User user) {
        this.user = (CassandraUserProxy) user;

        return this;
    }

    @Override
    public CassandraClientOrderBuilder deliveryName(String deliveryName) {
        this.deliveryName = deliveryName;

        return this;
    }

    @Override
    public CassandraClientOrderBuilder deliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;

        return this;
    }

    @Override
    public CassandraClientOrderBuilder deliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;

        return this;
    }

    @Override
    public CassandraClientOrderBuilder deliveryState(String deliveryState) {
        this.deliveryState = deliveryState;

        return this;
    }

    @Override
    public CassandraClientOrderBuilder deliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;

        return this;
    }

    @Override
    public CassandraClientOrderBuilder ccNumber(String ccNumber) {
        this.ccNumber = ccNumber;

        return this;
    }

    @Override
    public CassandraClientOrderBuilder ccExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;

        return this;
    }

    @Override
    public CassandraClientOrderBuilder ccCVV(String ccCVV) {
        this.ccCVV = ccCVV;

        return this;
    }

    @Override
    public <T extends Taco> CassandraClientOrderBuilder tacos(List<T> tacos) {
        this.tacos = (List<CassandraTacoProxy>) tacos;

        return this;
    }

    @Override
    public CassandraClientOrderProxy build() {
        return this.originalClientOrder
            .orElseGet(() -> clientOrderProxyFactory.create(
                new CassandraClientOrder()
                    .setId(UUID.randomUUID())
                    .setPlacedAt(new Date())
            ))
            .setUser(user)
            .setTacos(tacos)
            .setDeliveryCity(deliveryCity)
            .setDeliveryName(deliveryName)
            .setDeliveryState(deliveryState)
            .setDeliveryStreet(deliveryStreet)
            .setDeliveryZip(deliveryZip)
            .setCcNumber(ccNumber)
            .setCcCVV(ccCVV)
            .setCcExpiration(ccExpiration);
    }
}
