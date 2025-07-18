package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.ClientOrderLoader;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.provider.TacoLoaderProvider;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraTacoProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.TacoId;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;

import java.util.*;
import java.util.stream.Collectors;

public class CassandraClientOrderProxy implements ClientOrder {

    private final CassandraClientOrder clientOrder;

    private CassandraUserProxy user;
    private List<CassandraTacoProxy> tacos;

    private final TacoLoaderProvider tacoLoaderProvider;
    private final CassandraTacoProxyFactoryProvider factoryProvider;

    public CassandraClientOrderProxy(
        CassandraClientOrder clientOrder,
        TacoLoaderProvider tacoLoaderProvider,
        CassandraTacoProxyFactoryProvider factoryProvider
    ) {
        this.clientOrder = clientOrder;

        this.tacoLoaderProvider = tacoLoaderProvider;
        this.factoryProvider = factoryProvider;
    }

    public CassandraClientOrder unwrap() {
        return clientOrder;
    }

    public CassandraClientOrderProxy setCcCVV(String ccCVV) {
        clientOrder.setCcCVV(ccCVV);

        return this;
    }

    @Override
    public String getCcCVV() {
        return clientOrder.getCcCVV();
    }

    public CassandraClientOrderProxy setCcExpiration(String ccExpiration) {
        clientOrder.setCcExpiration(ccExpiration);

        return this;
    }

    @Override
    public String getCcExpiration() {
        return clientOrder.getCcExpiration();
    }

    public CassandraClientOrderProxy setCcNumber(String ccNumber) {
        clientOrder.setCcNumber(ccNumber);

        return this;
    }

    @Override
    public String getCcNumber() {
        return clientOrder.getCcNumber();
    }

    public CassandraClientOrderProxy setDeliveryZip(String deliveryZip) {
        clientOrder.setDeliveryZip(deliveryZip);

        return this;
    }

    @Override
    public String getDeliveryZip() {
        return clientOrder.getDeliveryZip();
    }

    public CassandraClientOrderProxy setDeliveryState(String deliveryState) {
        clientOrder.setDeliveryState(deliveryState);

        return this;
    }

    @Override
    public String getDeliveryState() {
        return clientOrder.getDeliveryState();
    }

    public CassandraClientOrderProxy setDeliveryCity(String deliveryCity) {
        clientOrder.setDeliveryCity(deliveryCity);

        return this;
    }

    @Override
    public String getDeliveryCity() {
        return clientOrder.getDeliveryCity();
    }

    public CassandraClientOrderProxy setDeliveryStreet(String deliveryStreet) {
        clientOrder.setDeliveryStreet(deliveryStreet);

        return this;
    }

    @Override
    public String getDeliveryStreet() {
        return clientOrder.getDeliveryStreet();
    }

    public CassandraClientOrderProxy setDeliveryName(String deliveryName) {
        clientOrder.setDeliveryName(deliveryName);

        return this;
    }

    @Override
    public String getDeliveryName() {
        return clientOrder.getDeliveryName();
    }

    @Override
    public Date getPlacedAt() {
        return clientOrder.getPlacedAt();
    }

    @Override
    public UUID getId() {
        return clientOrder.getId();
    }

    @Override
    public User getUser() {
        if (Objects.isNull(user)) {
            user = ((ClientOrderLoader) tacoLoaderProvider.resolve(CassandraClientOrder.class))
                .loadUser(clientOrder.getUserId())
                .map(((CassandraUserProxyFactory) factoryProvider.resolve(CassandraUserImpl.class))::create)
                .orElse(null);
        }

        return user;
    }

    public CassandraClientOrderProxy setUser(CassandraUserProxy user) {
        this.user = user;

        clientOrder.setUserId(
            Optional.ofNullable(user)
                .map(CassandraUserProxy::getId)
                .map(UserId::new)
                .orElse(null)
        );

        return this;
    }

    @Override
    public List<CassandraTacoProxy> getTacos() {
        if (Objects.isNull(tacos)) {
            CassandraTacoProxyFactory factory = factoryProvider.resolve(CassandraTaco.class);

            tacos = ((ClientOrderLoader) tacoLoaderProvider.resolve(CassandraClientOrder.class))
                .loadTacos(clientOrder.getTacoIds()).stream()
                .map(factory::create)
                .toList();
        }

        return tacos;
    }

    public CassandraClientOrderProxy setTacos(List<CassandraTacoProxy> tacos) {
        this.tacos = Optional.ofNullable(tacos).orElseGet(ArrayList::new);

        clientOrder.setTacoIds(
            this.tacos.stream()
                .map(Taco::getId)
                .map(TacoId::new)
                .collect(Collectors.toList())
        );

        return this;
    }

    @Override
    public void addTaco(Taco taco) {
        if (Objects.isNull(taco)) {
            return;
        }

        if (Objects.isNull(tacos)) {
            tacos = new ArrayList<>();
        }

        tacos.add((CassandraTacoProxy) taco);
        clientOrder.addTacoId(new TacoId(taco.getId()));
    }
}
