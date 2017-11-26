/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.Product;
import org.mihy.gowma.model.ProductImage;
import org.mihy.gowma.model.search.ProductSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> get(Integer productId) {
        return productRepository.getById(productId);
    }

    public Product create(Product product) {
        return productRepository.create(product);
    }

    public Product update(Product product) {
        return productRepository.update(product);
    }

    public void delete(Integer productId) {
        productRepository.delete(productId);
    }

    public List<Product> searchProducts(ProductSearchRequest productSearchRequest) {
        return productRepository.findAll(productSearchRequest);
    }

}
