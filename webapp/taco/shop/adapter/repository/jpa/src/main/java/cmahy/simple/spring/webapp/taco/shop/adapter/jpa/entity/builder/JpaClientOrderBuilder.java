package cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.builder;

import cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.domain.JpaClientOrder;
import cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.domain.JpaTaco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilder;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaUser;
import cmahy.simple.spring.webapp.user.kernel.domain.User;

import java.util.*;

public class JpaClientOrderBuilder implements ClientOrderBuilder<JpaClientOrder> {

    private Optional<JpaClientOrder> originalClientOrder = Optional.empty();

    private JpaUser user;

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;

    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<JpaTaco> tacos;
    
    public JpaClientOrderBuilder() {}

    public JpaClientOrderBuilder(JpaClientOrder clientOrder) {
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
    public JpaClientOrderBuilder user(User user) {
        this.user = (JpaUser) user;

        return this;
    }

    @Override
    public JpaClientOrderBuilder deliveryName(String deliveryName) {
        this.deliveryName = deliveryName;

        return this;
    }

    @Override
    public JpaClientOrderBuilder deliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;

        return this;
    }

    @Override
    public JpaClientOrderBuilder deliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;

        return this;
    }

    @Override
    public JpaClientOrderBuilder deliveryState(String deliveryState) {
        this.deliveryState = deliveryState;

        return this;
    }

    @Override
    public JpaClientOrderBuilder deliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;

        return this;
    }

    @Override
    public JpaClientOrderBuilder ccNumber(String ccNumber) {
        this.ccNumber = ccNumber;

        return this;
    }

    @Override
    public JpaClientOrderBuilder ccExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;

        return this;
    }

    @Override
    public JpaClientOrderBuilder ccCVV(String ccCVV) {
        this.ccCVV = ccCVV;

        return this;
    }

    @Override
    public <T extends Taco> JpaClientOrderBuilder tacos(List<T> tacos) {
        this.tacos = (List<JpaTaco>) tacos;

        return this;
    }

    @Override
    public JpaClientOrder build() {
        return originalClientOrder
            .orElseGet(JpaClientOrder::new)
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
