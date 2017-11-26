/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model.search;

import org.mihy.gowma.constants.InventoryStatus;

import java.util.Optional;

/**
 * Created by gdeepu on 24/11/17.
 */
public class ProductSearchRequest extends BaseSearchRequest {

    private Optional<Integer> categoryId;
    private Optional<String> name;
    private Optional<Boolean> isActive;
    private Optional<Double> lowerPrice;
    private Optional<Double> higherPrice;
    private Optional<Integer> viewCount;
    private Optional<InventoryStatus> inventoryStatus;



    public Optional<Integer> getViewCount() {
        return viewCount;
    }

    public void setViewCount(Optional<Integer> viewCount) {
        this.viewCount = viewCount;
    }

    public void setCategoryId(Optional<Integer> categoryId) {
        this.categoryId = categoryId;
    }

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public Optional<Boolean> getIsActive() {
        return isActive;
    }

    public void setIsActive(Optional<Boolean> isActive) {
        this.isActive = isActive;
    }

    public Optional<Double> getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(Optional<Double> lowerPrice) {
        this.lowerPrice = lowerPrice;
    }

    public Optional<Double> getHigherPrice() {
        return higherPrice;
    }

    public void setHigherPrice(Optional<Double> higherPrice) {
        this.higherPrice = higherPrice;
    }

    public Optional<Integer> getCategoryId() {

        return categoryId;
    }

    public Optional<InventoryStatus> getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(Optional<InventoryStatus> inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }
}
