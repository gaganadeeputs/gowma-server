/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.AuthenticatedUser;
import org.mihy.gowma.model.TaxDetail;
import org.mihy.gowma.repository.TaxDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaxDetailService {

    @Autowired
    private TaxDetailRepository taxDetailRepository;

    public List<TaxDetail> getTaxDetailsForCategoryIdRProductId(Integer categoryId, Integer productId) {
        return taxDetailRepository.getTaxDetailsForCategoryIdRProductId(categoryId, productId);
    }

    public TaxDetail update(Integer taxDetailId, TaxDetail taxDetail) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taxDetail.setCreatedBy(authenticatedUser.getId());
        taxDetail.setCreatedDate(LocalDateTime.now());
        return taxDetailRepository.update(taxDetail);
    }

    public TaxDetail create(TaxDetail taxDetail) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taxDetail.setCreatedBy(authenticatedUser.getId());
        taxDetail.setCreatedDate(LocalDateTime.now());
        return taxDetailRepository.create(taxDetail);
    }

    public void delete(Integer taxDetailId) {
        taxDetailRepository.delete(taxDetailId);
    }
}
