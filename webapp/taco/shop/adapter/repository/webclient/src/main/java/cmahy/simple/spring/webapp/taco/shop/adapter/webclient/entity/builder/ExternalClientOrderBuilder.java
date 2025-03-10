package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.builder;

import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.domain.ExternalClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.domain.ExternalTaco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilder;
import cmahy.simple.spring.webapp.user.adapter.webclient.entity.domain.ExternalUser;
import cmahy.simple.spring.webapp.user.kernel.domain.User;

import java.util.*;

public class ExternalClientOrderBuilder implements ClientOrderBuilder<ExternalClientOrder> {
    
    private Optional<ExternalClientOrder> originalClientOrder = Optional.empty();

    private ExternalUser user;

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;

    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<ExternalTaco> tacos;

    public ExternalClientOrderBuilder() {}

    public ExternalClientOrderBuilder(ExternalClientOrder clientOrder) {
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
    public ExternalClientOrderBuilder user(User user) {
        this.user = (ExternalUser) user;

        return this;
    }

    @Override
    public ExternalClientOrderBuilder deliveryName(String deliveryName) {
        this.deliveryName = deliveryName;

        return this;
    }

    @Override
    public ExternalClientOrderBuilder deliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;

        return this;
    }

    @Override
    public ExternalClientOrderBuilder deliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;

        return this;
    }

    @Override
    public ExternalClientOrderBuilder deliveryState(String deliveryState) {
        this.deliveryState = deliveryState;

        return this;
    }

    @Override
    public ExternalClientOrderBuilder deliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;

        return this;
    }

    @Override
    public ExternalClientOrderBuilder ccNumber(String ccNumber) {
        this.ccNumber = ccNumber;

        return this;
    }

    @Override
    public ExternalClientOrderBuilder ccExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;

        return this;
    }

    @Override
    public ExternalClientOrderBuilder ccCVV(String ccCVV) {
        this.ccCVV = ccCVV;

        return this;
    }

    @Override
    public <T extends Taco> ExternalClientOrderBuilder tacos(List<T> tacos) {
        this.tacos = (List<ExternalTaco>) tacos;

        return this;
    }

    @Override
    public ExternalClientOrder build() {
        return originalClientOrder
            .orElseGet(ExternalClientOrder::new)
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
