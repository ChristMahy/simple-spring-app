package cmahy.webapp.taco.shop.adapter.webclient.entity.builder;

import cmahy.webapp.taco.shop.adapter.webclient.entity.ExternalClientOrder;
import cmahy.webapp.taco.shop.adapter.webclient.entity.ExternalTaco;
import cmahy.webapp.taco.shop.kernel.domain.Taco;
import cmahy.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilder;
import cmahy.webapp.user.adapter.webclient.entity.ExternalUser;
import cmahy.webapp.user.kernel.domain.User;

import java.util.*;

public class ExternalClientOrderBuilder implements ClientOrderBuilder<ExternalClientOrder> {
    
    private Optional<ExternalClientOrder> originalClientOrder = Optional.empty();

    private ExternalUser user;

    private Date placedAt;

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
    }

    @Override
    public ExternalClientOrderBuilder user(User user) {
        this.user = (ExternalUser) user;

        return this;
    }

    @Override
    public ExternalClientOrderBuilder placedAt(Date placedAt) {
        this.placedAt = placedAt;

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
            .setPlacedAt(this.placedAt)
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
