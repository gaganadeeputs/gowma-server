/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.ProductCategory;
import org.mihy.gowma.model.search.ProductCategorySearchRequest;
import org.mihy.gowma.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getProductCategoriesForParentId(Integer parentCategoryId) {
        return productCategoryRepository.getProductCategoriesForParentId(parentCategoryId);
    }

    public ProductCategory create(ProductCategory productCategory) {
        return productCategoryRepository.create(productCategory);
    }

    public ProductCategory update(ProductCategory productCategory) {
        return productCategoryRepository.update(productCategory);
    }

    public void delete(Integer productCategory) {
        productCategoryRepository.delete(productCategory);
    }

    public List<ProductCategory> searchProductCategories(ProductCategorySearchRequest productCategorySearchRequest) {
        return productCategoryRepository.findAll(productCategorySearchRequest);

    }
}
