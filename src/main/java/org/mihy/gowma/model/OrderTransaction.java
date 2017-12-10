/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model;


public class OrderTransaction extends BaseModel {

    private PaymentDetail paymentDetail;
    private String orderTxnId;
    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

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


}
