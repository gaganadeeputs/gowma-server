/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProductWishListRepository extends BaseRepository {
    public List<Product> getWishedProductsForUserId(Integer userId) {
        return null;
    }

    public void addProductToWishList(Integer userId) {
    }

    public void removeProductFromWishList(Integer userId) {
    }

    public void clearUserWishList(Integer userId) {
    }
}
