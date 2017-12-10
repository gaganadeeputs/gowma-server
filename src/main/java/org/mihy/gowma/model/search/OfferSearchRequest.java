/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model.search;

import java.time.LocalDateTime;

public class OfferSearchRequest extends BaseSearchRequest {


    private String name;
    private Double lowerPercentage;
    private Double higherPercentage;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private Integer productId;
    private Integer categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Double getLowerPercentage() {
        return lowerPercentage;
    }

    public void setLowerPercentage(Double lowerPercentage) {
        this.lowerPercentage = lowerPercentage;
    }

    public Double getHigherPercentage() {
        return higherPercentage;
    }

    public void setHigherPercentage(Double higherPercentage) {
        this.higherPercentage = higherPercentage;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
