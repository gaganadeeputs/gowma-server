/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model;

public class ProductInventory extends BaseModel {

    private int availableCount;
    private int soldCount;
    private InventoryStatus status;

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    public enum InventoryStatus {

        ACTIVE,
        OUT_OF_STOCK,
        ARRAIVING_SOON,
        PRE_ORDER
    }
}
