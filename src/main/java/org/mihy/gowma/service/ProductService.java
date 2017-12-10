/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.Product;
import org.mihy.gowma.model.AuthenticatedUser;
import org.mihy.gowma.model.search.ProductSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product get(Integer productId) {
        return productRepository.getById(productId);
    }

    public Product create(Product product) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setCreatedBy(authenticatedUser.getId());
        product.setCreatedDate(LocalDateTime.now());
        return productRepository.create(product);
    }

    public Product update(Product product) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setLastModifiedBy(authenticatedUser.getId());
        product.setLastModifiedDate(LocalDateTime.now());
        return productRepository.update(product);
    }

    public void delete(Integer productId) {
        productRepository.delete(productId);
    }

    public List<Product> searchProducts(ProductSearchRequest productSearchRequest) {
        return productRepository.findAll(productSearchRequest);
    }

}
