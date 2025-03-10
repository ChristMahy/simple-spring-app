package cmahy.simple.spring.webapp.taco.shop.kernel.domain;

import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.UserStub;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

public class ClientOrderStub implements ClientOrder {

    private UUID id;

    private UserStub user;

    private Date placedAt;

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;

    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<TacoStub> tacos;

    @Override
    public UUID getId() {
        return id;
    }

    public ClientOrderStub setId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public UserStub getUser() {
        return user;
    }

    public ClientOrderStub setUser(UserStub user) {
        this.user = user;
        return this;
    }

    @Override
    public Date getPlacedAt() {
        return placedAt;
    }

    public ClientOrderStub setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
        return this;
    }

    @Override
    public String getDeliveryName() {
        return deliveryName;
    }

    public ClientOrderStub setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
        return this;
    }

    @Override
    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public ClientOrderStub setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
        return this;
    }

    @Override
    public String getDeliveryCity() {
        return deliveryCity;
    }

    public ClientOrderStub setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
        return this;
    }

    @Override
    public String getDeliveryState() {
        return deliveryState;
    }

    public ClientOrderStub setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
        return this;
    }

    @Override
    public String getDeliveryZip() {
        return deliveryZip;
    }

    public ClientOrderStub setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
        return this;
    }

    @Override
    public String getCcNumber() {
        return ccNumber;
    }

    public ClientOrderStub setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
        return this;
    }

    @Override
    public String getCcExpiration() {
        return ccExpiration;
    }

    public ClientOrderStub setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
        return this;
    }

    @Override
    public String getCcCVV() {
        return ccCVV;
    }

    public ClientOrderStub setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
        return this;
    }

    public List<TacoStub> getTacos() {
        return tacos;
    }

    public ClientOrderStub setTacos(List<TacoStub> tacos) {
        this.tacos = Objects.isNull(tacos) ? null : new ArrayList<>(tacos);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", getId())
            .append("user", getUser())
            .append("placedAt", getPlacedAt())
            .append("deliveryName", getDeliveryName())
            .append("deliveryStreet", getDeliveryStreet())
            .append("deliveryCity", getDeliveryCity())
            .append("deliveryState", getDeliveryState())
            .append("deliveryZip", getDeliveryZip())
            .append("ccNumber", getCcNumber())
            .append("ccExpiration", getCcExpiration())
            .append("ccCVV", getCcCVV())
            .toString();
    }

    @Override
    public void addTaco(Taco taco) {
        if (Objects.isNull(taco)) {
            return;
        }

        if (Objects.isNull(this.tacos)) {
            this.tacos = new ArrayList<>();
        }

        this.tacos.add((TacoStub) taco);
    }
}