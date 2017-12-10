/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model.search;

import org.mihy.gowma.model.Order;
import org.mihy.gowma.model.OrderShippingDetail;

import java.time.LocalDateTime;

public class OrderSearchRequest extends BaseSearchRequest {

    private Integer userId;
    private LocalDateTime startOrderDate;
    private LocalDateTime endOrderDate;
    private Order.OrderStatus orderStatus;
    private OrderShippingDetail.ShippingStatus shippingStatus;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartOrderDate() {
        return startOrderDate;
    }

    public void setStartOrderDate(LocalDateTime startOrderDate) {
        this.startOrderDate = startOrderDate;
    }

    public LocalDateTime getEndOrderDate() {
        return endOrderDate;
    }

    public void setEndOrderDate(LocalDateTime endOrderDate) {
        this.endOrderDate = endOrderDate;
    }

    public Order.OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Order.OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderShippingDetail.ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(OrderShippingDetail.ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;
    }
}
