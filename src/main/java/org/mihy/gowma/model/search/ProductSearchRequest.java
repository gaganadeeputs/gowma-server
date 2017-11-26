/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model.search;

import org.mihy.gowma.constants.InventoryStatus;

public class ProductSearchRequest extends BaseSearchRequest {

    private Integer categoryId;
    private String name;
    private Boolean isActive;
    private Double lowerPrice;
    private Double higherPrice;
    private Integer viewCount;
    private InventoryStatus inventoryStatus;


    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Double getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(Double lowerPrice) {
        this.lowerPrice = lowerPrice;
    }

    public Double getHigherPrice() {
        return higherPrice;
    }

    public void setHigherPrice(Double higherPrice) {
        this.higherPrice = higherPrice;
    }

    public Integer getCategoryId() {

        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public InventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(InventoryStatus inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }
}
