/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model;


public class PaymentDetail extends BaseModel {


    private PaymentMode paymentMode;
    private String description;
    private String paymentGateway;
    private String getPaymentGatewayTxnId;

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public String getGetPaymentGatewayTxnId() {
        return getPaymentGatewayTxnId;
    }

    public void setGetPaymentGatewayTxnId(String getPaymentGatewayTxnId) {
        this.getPaymentGatewayTxnId = getPaymentGatewayTxnId;
    }

    public enum PaymentMode {
        ONLINE,
        CREDIT_CARD
    }
}
