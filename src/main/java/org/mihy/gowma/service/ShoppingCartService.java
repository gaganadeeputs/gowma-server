/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.ShoppingCartItem;
import org.mihy.gowma.repository.ShoppingCartItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartItemsRepository shoppingCartItemsRepository;

    public List<ShoppingCartItem> getShoppingCartItemsForUserId(Integer userId) {
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemsRepository.getCartItemsForUserId(userId);
        return shoppingCartItems;
    }

    public ShoppingCartItem addToShoppingCartItems(ShoppingCartItem shoppingCartItem) {
        return shoppingCartItemsRepository.create(shoppingCartItem);

    }

    public void removeFromShoppingCartItems(Integer userId, Integer shoppingCartItemId) {
        shoppingCartItemsRepository.delete(userId, shoppingCartItemId);
    }

    public ShoppingCartItem updateShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        return shoppingCartItemsRepository.update(shoppingCartItem);
    }


    public void clearAllShoppingCartListItemsForUserId(Integer userId) {
        shoppingCartItemsRepository.clearAllShoppingCartListItemsForUserId(userId);
    }
}
