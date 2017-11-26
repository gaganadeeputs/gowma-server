/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;


import org.mihy.gowma.model.Product;
import org.mihy.gowma.model.SecureUser;
import org.mihy.gowma.model.ShoppingCartItem;
import org.mihy.gowma.model.User;
import org.mihy.gowma.repository.CartListRepository;
import org.mihy.gowma.repository.ProductWishListRepository;
import org.mihy.gowma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartListRepository cartListRepository;

    @Autowired
    private ProductWishListRepository productWishListRepository;


    public User create(User user) {
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        return userRepository.create(user);
    }

    public User get(int userId) {
        return userRepository.get(userId);
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public void delete(int userId) {
        userRepository.delete(userId);
    }

    public UserDetails findByEmail(String email) {
        return new SecureUser(userRepository.findByEmail(email));
    }

    public List<Product> getWishedProductsForUserId(Integer userId) {
        return productWishListRepository.getWishedProductsForUserId(userId);
    }

    public void addProductToWishList(Integer userId, Integer productId) {
        productWishListRepository.addProductToWishList(userId);
    }

    public void removeProductFromWishList(Integer userId, Integer productId) {
        productWishListRepository.removeProductFromWishList(userId);
    }

    public void clearUserWishList(Integer userId) {
        productWishListRepository.clearUserWishList(userId);
    }

    public List<ShoppingCartItem> getShoppingCartForUserId(Integer userId) {
        List<ShoppingCartItem> shoppingCartItems = cartListRepository.getCartItemsForUserId(userId);
        return shoppingCartItems;
    }

    public ShoppingCartItem addToShoppingCart(Integer userId, ShoppingCartItem shoppingCartItem) {
        return cartListRepository.create(userId, shoppingCartItem);

    }

    public void removeFromShoppingCart(Integer userId, Integer shoppingCartItemId) {
        cartListRepository.delete(userId, shoppingCartItemId);
    }

    public ShoppingCartItem updateShoppingCart(Integer userId, Integer shoppingCartItemId, ShoppingCartItem shoppingCartItem) {
        return cartListRepository.update(userId, shoppingCartItemId, shoppingCartItem);
    }
}