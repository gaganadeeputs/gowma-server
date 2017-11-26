/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model;


public class OrderTransaction extends BaseModel {

    private PaymentDetail paymentDetail;
    private String orderTxnId;
    private OrderShippingDetail orderShippingDetail;

    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public String getOrderTxnId() {
        return orderTxnId;
    }

    public void setOrderTxnId(String orderTxnId) {
        this.orderTxnId = orderTxnId;
    }

    public OrderShippingDetail getOrderShippingDetail() {
        return orderShippingDetail;
    }

    public void setOrderShippingDetail(OrderShippingDetail orderShippingDetail) {
        this.orderShippingDetail = orderShippingDetail;
    }

}
