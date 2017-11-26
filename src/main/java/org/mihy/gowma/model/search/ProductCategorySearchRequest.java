/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model.search;

import java.util.Optional;

public class ProductCategorySearchRequest  extends BaseSearchRequest {

    private Optional<Integer> parentCategoryId;
    private Optional<String> name;
    private Optional<Boolean> enabled;


    public Optional<Integer> getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Optional<Integer> parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public Optional<Boolean> getEnabled() {
        return enabled;
    }

    public void setEnabled(Optional<Boolean> enabled) {
        this.enabled = enabled;
    }
}
