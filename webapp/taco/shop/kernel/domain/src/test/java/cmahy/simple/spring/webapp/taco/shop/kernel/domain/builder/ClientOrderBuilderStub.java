package cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.*;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.UserStub;

import java.util.List;
import java.util.Optional;

public class ClientOrderBuilderStub implements ClientOrderBuilder<ClientOrderStub> {

    private Optional<ClientOrderStub> originalClientOrder = Optional.empty();

    private UserStub user;

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;

    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<TacoStub> tacos;

    public ClientOrderBuilderStub() {}

    public ClientOrderBuilderStub(ClientOrderStub clientOrder) {
        this.originalClientOrder = Optional.ofNullable(clientOrder);

        this.originalClientOrder.ifPresent(co -> {
            this.user = co.getUser();

            this.deliveryName = co.getDeliveryName();
            this.deliveryStreet = co.getDeliveryStreet();
            this.deliveryCity = co.getDeliveryCity();
            this.deliveryState = co.getDeliveryState();
            this.deliveryZip = co.getDeliveryZip();

            this.ccNumber = co.getCcNumber();
            this.ccExpiration = co.getCcExpiration();
            this.ccCVV = co.getCcCVV();

            this.tacos = co.getTacos();
        });
    }

    @Override
    public ClientOrderBuilderStub user(User user) {
        this.user = (UserStub) user;

        return this;
    }

    @Override
    public ClientOrderBuilderStub deliveryName(String deliveryName) {
        this.deliveryName = deliveryName;

        return this;
    }

    @Override
    public ClientOrderBuilderStub deliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;

        return this;
    }

    @Override
    public ClientOrderBuilderStub deliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;

        return this;
    }

    @Override
    public ClientOrderBuilderStub deliveryState(String deliveryState) {
        this.deliveryState = deliveryState;

        return this;
    }

    @Override
    public ClientOrderBuilderStub deliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;

        return this;
    }

    @Override
    public ClientOrderBuilderStub ccNumber(String ccNumber) {
        this.ccNumber = ccNumber;

        return this;
    }

    @Override
    public ClientOrderBuilderStub ccExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;

        return this;
    }

    @Override
    public ClientOrderBuilderStub ccCVV(String ccCVV) {
        this.ccCVV = ccCVV;

        return this;
    }

    @Override
    public <T extends Taco> ClientOrderBuilderStub tacos(List<T> tacos) {
        this.tacos = (List<TacoStub>) tacos;

        return this;
    }

    @Override
    public ClientOrderStub build() {
        return originalClientOrder
            .orElseGet(ClientOrderStub::new)
            .setUser(this.user)
            .setDeliveryName(this.deliveryName)
            .setDeliveryStreet(this.deliveryStreet)
            .setDeliveryCity(this.deliveryCity)
            .setDeliveryState(this.deliveryState)
            .setDeliveryZip(this.deliveryZip)
            .setCcNumber(this.ccNumber)
            .setCcExpiration(this.ccExpiration)
            .setCcCVV(this.ccCVV)
            .setTacos(this.tacos);
    }
}
