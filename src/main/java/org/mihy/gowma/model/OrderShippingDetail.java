/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model;

public class OrderShippingDetail extends BaseModel {

    private Integer orderTxnId;
    private ShippingStatus shippingStatus;
    private String description;

    public Integer getOrderTxnId() {
        return orderTxnId;
    }

    public void setOrderTxnId(Integer orderTxnId) {
        this.orderTxnId = orderTxnId;
    }

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum ShippingStatus {
        TRANSIT,
        DISPATCHED,
        DELIVERED
    }
}
