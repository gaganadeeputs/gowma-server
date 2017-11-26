/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.ProductImage;
import org.mihy.gowma.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductImageService {

    @Autowired
    ProductImageRepository productImageRepository;

    public void deleteByProductIdNId(Integer productId, Integer productImageId) {
        productImageRepository.deleteByProductIdNId(productId, productId);
    }

    public List<ProductImage> getAllProductImagesByProductId(Integer productId) {
        return productImageRepository.getByProductId(productId);
    }

    public List<ProductImage> createForProductId(Integer productId, List<ProductImage> productImages) {
        return productImageRepository.create(productImages);
    }


    public ProductImage update(ProductImage productImage) {
        return productImageRepository.update(productImage);
    }
}
