package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.TacoId;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.cql.Statement;
import org.springframework.data.cassandra.core.mapping.*;
import org.springframework.data.cassandra.core.mapping.event.BeforeSaveCallback;

import java.util.*;

@Table("client_order")
public class CassandraClientOrder implements BeforeSaveCallback<CassandraClientOrder> {

    @PrimaryKey
    protected UUID id;

    protected UserId userId;

    protected Date placedAt;

    protected String deliveryName;
    protected String deliveryStreet;
    protected String deliveryCity;
    protected String deliveryState;
    protected String deliveryZip;

    protected String ccNumber;
    protected String ccExpiration;
    protected String ccCVV;

    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.BIGINT)
    protected List<TacoId> tacoIds = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public CassandraClientOrder setId(UUID id) {
        this.id = id;
        return this;
    }

    public UserId getUserId() {
        return userId;
    }

    public CassandraClientOrder setUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public Date getPlacedAt() {
        return placedAt;
    }

    public CassandraClientOrder setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
        return this;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public CassandraClientOrder setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
        return this;
    }

    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public CassandraClientOrder setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
        return this;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public CassandraClientOrder setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
        return this;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public CassandraClientOrder setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
        return this;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public CassandraClientOrder setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
        return this;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public CassandraClientOrder setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
        return this;
    }

    public String getCcExpiration() {
        return ccExpiration;
    }

    public CassandraClientOrder setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
        return this;
    }

    public String getCcCVV() {
        return ccCVV;
    }

    public CassandraClientOrder setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
        return this;
    }

    public List<TacoId> getTacoIds() {
        return tacoIds;
    }

    public CassandraClientOrder setTacoIds(List<TacoId> tacoIds) {
        this.tacoIds = tacoIds;
        return this;
    }

    @Override
    public CassandraClientOrder onBeforeSave(CassandraClientOrder clientOrder, CqlIdentifier tableName, Statement<?> statement) {
        if (Objects.nonNull(clientOrder) && Objects.isNull(clientOrder.getPlacedAt())) {
            clientOrder.setPlacedAt(new Date());
        }

        return clientOrder;
    }

    public void addTacoId(TacoId tacoId) {
        if (Objects.isNull(tacoId)) {
            return;
        }

        if (Objects.isNull(this.tacoIds)) {
            this.tacoIds = new ArrayList<>();
        }

        this.tacoIds.add(tacoId);
    }
}
