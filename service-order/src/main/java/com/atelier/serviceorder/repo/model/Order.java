package com.atelier.serviceorder.repo.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
public final class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private OrderPoints orderPoint;
    @NotNull
    private long clientId;
    @NotNull
    private long serviceId;
    private Timestamp timestamp;
    @NotNull
    private long masterId;

    public Order() {

    }

    public Order(OrderPoints orderPoint, long clientId, long serviceId, long masterId) {
        this.orderPoint = orderPoint;
        this.clientId = clientId;
        this.serviceId = serviceId;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.masterId = masterId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderPoints getOrderPoint() {
        return orderPoint;
    }

    public void setOrderPoint(OrderPoints orderPoint) {
        this.orderPoint = orderPoint;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public long getMasterId() {
        return masterId;
    }

    public void setMasterId(long masterId) {
        this.masterId = masterId;
    }
}
