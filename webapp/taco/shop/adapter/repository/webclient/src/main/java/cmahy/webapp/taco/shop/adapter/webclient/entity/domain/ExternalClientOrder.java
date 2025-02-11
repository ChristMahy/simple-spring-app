package cmahy.webapp.taco.shop.adapter.webclient.entity.domain;

import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.webapp.taco.shop.kernel.domain.Taco;
import cmahy.webapp.user.kernel.domain.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

public class ExternalClientOrder implements ClientOrder {

    private UUID id;

    private User user;

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

    @Override
    public UUID getId() {
        return id;
    }

    public ExternalClientOrder setId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public User getUser() {
        return user;
    }

    public ExternalClientOrder setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public Date getPlacedAt() {
        return placedAt;
    }

    public ExternalClientOrder setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
        return this;
    }

    @Override
    public String getDeliveryName() {
        return deliveryName;
    }

    public ExternalClientOrder setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
        return this;
    }

    @Override
    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public ExternalClientOrder setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
        return this;
    }

    @Override
    public String getDeliveryCity() {
        return deliveryCity;
    }

    public ExternalClientOrder setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
        return this;
    }

    @Override
    public String getDeliveryState() {
        return deliveryState;
    }

    public ExternalClientOrder setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
        return this;
    }

    @Override
    public String getDeliveryZip() {
        return deliveryZip;
    }

    public ExternalClientOrder setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
        return this;
    }

    @Override
    public String getCcNumber() {
        return ccNumber;
    }

    public ExternalClientOrder setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
        return this;
    }

    @Override
    public String getCcExpiration() {
        return ccExpiration;
    }

    public ExternalClientOrder setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
        return this;
    }

    @Override
    public String getCcCVV() {
        return ccCVV;
    }

    public ExternalClientOrder setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
        return this;
    }

    public List<ExternalTaco> getTacos() {
        return tacos;
    }

    public ExternalClientOrder setTacos(List<ExternalTaco> tacos) {
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

        this.tacos.add((ExternalTaco) taco);
    }
}
