/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model;

import java.util.List;

public class OrderCreationWrapper extends BaseModel {

    private Order order;
    private List<OrderItem> orderItems;
    private OrderTransaction orderTransaction;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderTransaction getOrderTransaction() {
        return orderTransaction;
    }

    public void setOrderTransaction(OrderTransaction orderTransaction) {
        this.orderTransaction = orderTransaction;
    }
}
