/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.model.TaxDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaxDetailRepository extends BaseRepository {
    public List<TaxDetail> getTaxDetailsForCategoryIdNProductId(Integer categoryId, Integer productId) {
        return null;
    }

    public TaxDetail update(TaxDetail taxDetail) {
        return null;
    }

    public TaxDetail create(TaxDetail taxDetail) {
        return null;
    }

    public void delete(Integer taxDetailId) {

    }
}
