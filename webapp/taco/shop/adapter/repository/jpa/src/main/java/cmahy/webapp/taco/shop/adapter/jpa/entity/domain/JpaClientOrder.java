package cmahy.webapp.taco.shop.adapter.jpa.entity.domain;

import cmahy.webapp.taco.shop.kernel.domain.ClientOrder;
import cmahy.webapp.taco.shop.kernel.domain.Taco;
import cmahy.webapp.user.adapter.jpa.entity.domain.JpaUser;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

@Entity
@Table(name = "client_order")
public class JpaClientOrder implements ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    protected JpaUser user;

    protected Date placedAt;

    protected String deliveryName;
    protected String deliveryStreet;
    protected String deliveryCity;
    protected String deliveryState;
    protected String deliveryZip;

    protected String ccNumber;
    protected String ccExpiration;
    protected String ccCVV;

    @OneToMany(cascade = CascadeType.ALL)
    protected List<JpaTaco> tacos = new ArrayList<>();

    @Override
    public UUID getId() {
        return id;
    }

    public JpaClientOrder setId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public JpaUser getUser() {
        return user;
    }

    public JpaClientOrder setUser(JpaUser user) {
        this.user = user;
        return this;
    }

    @Override
    public Date getPlacedAt() {
        return placedAt;
    }

    public JpaClientOrder setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
        return this;
    }

    @Override
    public String getDeliveryName() {
        return deliveryName;
    }

    public JpaClientOrder setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
        return this;
    }

    @Override
    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public JpaClientOrder setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
        return this;
    }

    @Override
    public String getDeliveryCity() {
        return deliveryCity;
    }

    public JpaClientOrder setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
        return this;
    }

    @Override
    public String getDeliveryState() {
        return deliveryState;
    }

    public JpaClientOrder setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
        return this;
    }

    @Override
    public String getDeliveryZip() {
        return deliveryZip;
    }

    public JpaClientOrder setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
        return this;
    }

    @Override
    public String getCcNumber() {
        return ccNumber;
    }

    public JpaClientOrder setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
        return this;
    }

    @Override
    public String getCcExpiration() {
        return ccExpiration;
    }

    public JpaClientOrder setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
        return this;
    }

    @Override
    public String getCcCVV() {
        return ccCVV;
    }

    public JpaClientOrder setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
        return this;
    }

    @Override
    public List<JpaTaco> getTacos() {
        return tacos;
    }

    public JpaClientOrder setTacos(List<JpaTaco> tacos) {
        this.tacos = tacos;
        return this;
    }

    @PrePersist
    public void prePersist() {
        if (Objects.isNull(placedAt)) {
            this.placedAt = new Date();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("ccCVV", getCcCVV())
            .append("ccExpiration", getCcExpiration())
            .append("ccNumber", getCcNumber())
            .append("deliveryZip", getDeliveryZip())
            .append("deliveryState", getDeliveryState())
            .append("deliveryCity", getDeliveryCity())
            .append("deliveryStreet", getDeliveryStreet())
            .append("deliveryName", getDeliveryName())
            .append("placedAt", getPlacedAt())
            .append("user", getUser())
            .append("id", getId())
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

        this.tacos.add((JpaTaco) taco);
    }
}
