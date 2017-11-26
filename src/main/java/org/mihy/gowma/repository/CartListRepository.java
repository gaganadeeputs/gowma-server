/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.model.ShoppingCartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartListRepository extends BaseRepository {
    public ShoppingCartItem create(Integer userId, ShoppingCartItem shoppingCartItem) {
        return null;
    }

    public List<ShoppingCartItem> getCartItemsForUserId(Integer userId) {
        return null;
    }

    public void delete(Integer userId, Integer shoppingCartItemId) {

    }

    public ShoppingCartItem update(Integer userId, Integer shoppingCartItemId, ShoppingCartItem shoppingCartItem) {
        return null;
    }
}
