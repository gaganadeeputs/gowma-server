/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.UserWishedProduct;
import org.mihy.gowma.repository.ProductWishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWishListService {


    @Autowired
    private ProductWishListRepository productWishListRepository;

    public List<UserWishedProduct> getWishedProductsForUserId(Integer userId) {
        return productWishListRepository.getWishedProductsForUserId(userId);
    }

    public UserWishedProduct addProductToWishList(UserWishedProduct userWishedProduct) {
        return productWishListRepository.create(userWishedProduct);
    }

    public void removeProductFromWishList(Integer userId, Integer productId) {
        productWishListRepository.delete(userId, productId);
    }

    public void clearUserWishList(Integer userId) {
        productWishListRepository.clearUserWishList(userId);
    }


}
