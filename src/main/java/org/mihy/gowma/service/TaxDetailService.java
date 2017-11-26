/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.TaxDetail;
import org.mihy.gowma.repository.TaxDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxDetailService {

    @Autowired
    private TaxDetailRepository taxDetailRepository;

    public List<TaxDetail> getTaxDetailsForCategoryIdNProductId(Integer categoryId, Integer productId) {
        return taxDetailRepository.getTaxDetailsForCategoryIdNProductId(categoryId, productId);
    }

    public TaxDetail update(Integer taxDetailId, TaxDetail taxDetail) {
        return taxDetailRepository.update(taxDetail);
    }

    public TaxDetail create(TaxDetail taxDetail) {
        return taxDetailRepository.create(taxDetail);
    }

    public void delete(Integer taxDetailId) {
        taxDetailRepository.delete(taxDetailId);
    }
}
