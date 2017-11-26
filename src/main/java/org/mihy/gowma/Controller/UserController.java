/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.Controller;

import io.swagger.annotations.ApiOperation;
import org.mihy.gowma.constants.EndPoints;
import org.mihy.gowma.model.Product;
import org.mihy.gowma.model.ShoppingCartItem;
import org.mihy.gowma.model.User;
import org.mihy.gowma.model.UserAddress;
import org.mihy.gowma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Create a user", response = User.class)
    @PostMapping(EndPoints.User.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @ApiOperation(value = "Get a user by id", response = User.class)
    @GetMapping(EndPoints.User.USER_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId) {
        return userService.get(userId);
    }

    @ApiOperation(value = "Update a user by id", response = User.class)
    @PutMapping(EndPoints.User.USER_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                           @RequestBody User user) {
        user.setId(userId);
        return userService.update(user);
    }

    @ApiOperation(value = "Delete a user by id", response = User.class)
    @DeleteMapping(EndPoints.User.USER_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = EndPoints.PathVariable.USER_ID) int userId) {
        userService.delete(userId);

    }


    //USER ADDRESS APIS
    @ApiOperation(value = "Get a list of addresses by a user id")
    @GetMapping(EndPoints.User.USER_ADDRESS)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserAddress> getAddressesForUserId(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId) {
        return null;
    }


    @ApiOperation(value = "Add a new address to the user by id")
    @PostMapping(EndPoints.User.USER_ADDRESS)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public UserAddress createAddressForUserId(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                              @RequestBody UserAddress userAddress) {
        return null;
    }

    @ApiOperation(value = "Update a existing address of user by id")
    @PostMapping(EndPoints.User.USER_ADDRESS_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public UserAddress updateAddressForUserId(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                              @PathVariable(value = EndPoints.PathVariable.ADDRESS_ID) Integer addressId,
                                              @RequestBody UserAddress userAddress) {
        return null;
    }

    @ApiOperation(value = "Delete a address of a user by id ")
    @DeleteMapping(EndPoints.User.USER_ADDRESS_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddressForUserId(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                       @PathVariable(value = EndPoints.PathVariable.ADDRESS_ID) Integer addressId,
                                       @RequestBody UserAddress userAddress) {

    }

    //User wish list API
    @ApiOperation(value = "Get user wishlist by user id ")
    @GetMapping(EndPoints.User.USER_WISH_LIST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getWishedProductsForUserId(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId) {
        return userService.getWishedProductsForUserId(userId);
    }

    @ApiOperation(value = "Add a product with given id to the user")
    @PostMapping(EndPoints.User.USER_WISH_LIST_WITH_PRODUCT_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProductToWishList(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                     @PathVariable(value = EndPoints.PathVariable.PRODUCT_ID) Integer productId) {
        userService.addProductToWishList(userId, productId);
    }

    @ApiOperation(value = "Remove a product with given id from the user")
    @DeleteMapping(EndPoints.User.USER_WISH_LIST_WITH_PRODUCT_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProductToWishList(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                        @PathVariable(value = EndPoints.PathVariable.PRODUCT_ID) Integer productId) {
        userService.removeProductFromWishList(userId, productId);
    }

    @ApiOperation(value = "Clear the whole user wishList")
    @DeleteMapping(EndPoints.User.USER_WISH_LIST)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearUserWishList(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId) {
        userService.clearUserWishList(userId);
    }

    //User cart  API
    @ApiOperation(value = "Get a list of shopping cart items for a user by user id")
    @GetMapping(EndPoints.User.USER_CART)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingCartItem> getShoppingCartForUserId(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId) {
        return userService.getShoppingCartForUserId(userId);
    }

    @ApiOperation(value = "Add a shopping cart item to the user")
    @PostMapping(EndPoints.User.USER_CART)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartItem addToShoppingCart(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                              @RequestBody ShoppingCartItem shoppingCartItem) {
        return userService.addToShoppingCart(userId, shoppingCartItem);
    }

    @ApiOperation(value = "Update a shopping cart item by id for a user")
    @PutMapping(EndPoints.User.USER_CART_ITEM_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartItem updateShoppingCartItem(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                                   @PathVariable(value = EndPoints.PathVariable.SHOPPING_CART_ITEM_ID) Integer shoppingCartItemId,
                                                   @RequestBody ShoppingCartItem shoppingCartItem) {
        return userService.updateShoppingCart(userId, shoppingCartItemId, shoppingCartItem);
    }

    @ApiOperation(value = "Remove a shopping cart item by id for a user", response = User.class)
    @DeleteMapping(EndPoints.User.USER_CART_ITEM_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void removeFromShoppingCart(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                       @PathVariable(value = EndPoints.PathVariable.SHOPPING_CART_ITEM_ID) Integer shoppingCartItemId) {
        userService.removeFromShoppingCart(userId, shoppingCartItemId);
    }


}
