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
import org.mihy.gowma.model.UserWishedProduct;
import org.mihy.gowma.service.ShoppingCartService;
import org.mihy.gowma.service.UserAddressService;
import org.mihy.gowma.service.UserService;
import org.mihy.gowma.service.UserWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
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
public class UserController extends AbstractWebResponseController  {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private UserWishListService userWishListService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation(value = "Create a user", response = User.class)
    @PostMapping(EndPoints.User.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Validated User user) {
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
        return userAddressService.getAddressesForUserId(userId);
    }


    @ApiOperation(value = "Add a new address to the user by id")
    @PostMapping(EndPoints.User.USER_ADDRESS)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public UserAddress createAddressForUserId(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                              @RequestBody UserAddress userAddress) {
        userAddress.setUserId(userId);
        return userAddressService.createAddress(userAddress);
    }

    @ApiOperation(value = "Update a existing address of user by id")
    @PostMapping(EndPoints.User.USER_ADDRESS_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public UserAddress updateAddressForUserId(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                              @PathVariable(value = EndPoints.PathVariable.ADDRESS_ID) Integer addressId,
                                              @RequestBody UserAddress userAddress) {
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        return userAddressService.updateAddress(userAddress);
    }

    @ApiOperation(value = "Delete a address of a user by id ")
    @DeleteMapping(EndPoints.User.USER_ADDRESS_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddressForUserId(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                       @PathVariable(value = EndPoints.PathVariable.ADDRESS_ID) Integer addressId) {
        userAddressService.deleteForUserIdNAddressId(userId, addressId);

    }

    //User wish list API
    @ApiOperation(value = "Get user wishlist by user id ")
    @GetMapping(EndPoints.User.USER_WISH_LIST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<UserWishedProduct> getWishedProductsForUserId(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId) {
        return userWishListService.getWishedProductsForUserId(userId);
    }

    @ApiOperation(value = "Add a product with given id to the user")
    @PostMapping(EndPoints.User.USER_WISH_LIST_WITH_PRODUCT_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProductToWishList(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                     @PathVariable(value = EndPoints.PathVariable.PRODUCT_ID) Integer productId) {
        UserWishedProduct userWishedProduct = new UserWishedProduct();
        userWishedProduct.setUserId(userId);
        Product product = new Product();
        product.setId(productId);
        userWishedProduct.setProduct(product);
        userWishListService.addProductToWishList(userWishedProduct);
    }

    @ApiOperation(value = "Remove a product with given id from the user")
    @DeleteMapping(EndPoints.User.USER_WISH_LIST_WITH_PRODUCT_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProductToWishList(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                        @PathVariable(value = EndPoints.PathVariable.PRODUCT_ID) Integer productId) {
        userWishListService.removeProductFromWishList(userId, productId);
    }

    @ApiOperation(value = "Clear the whole user wishList")
    @DeleteMapping(EndPoints.User.USER_WISH_LIST)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearUserWishList(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId) {
        userWishListService.clearUserWishList(userId);
    }

    //User Shopping cart API
    @ApiOperation(value = "Get a list of shopping cart items for a user by user id")
    @GetMapping(EndPoints.User.USER_CART)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingCartItem> getShoppingCartForUserId(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId) {
        return shoppingCartService.getShoppingCartItemsForUserId(userId);
    }

    @ApiOperation(value = "Add a shopping cart item to the user")
    @PostMapping(EndPoints.User.USER_CART)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartItem addToShoppingCart(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                              @RequestBody ShoppingCartItem shoppingCartItem) {
        shoppingCartItem.setUserId(userId);
        return shoppingCartService.addToShoppingCartItems(shoppingCartItem);
    }

    @ApiOperation(value = "Update a shopping cart item by id for a user")
    @PutMapping(EndPoints.User.USER_CART_ITEM_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartItem updateShoppingCartItem(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                                   @PathVariable(value = EndPoints.PathVariable.SHOPPING_CART_ITEM_ID) Integer shoppingCartItemId,
                                                   @RequestBody ShoppingCartItem shoppingCartItem) {
        shoppingCartItem.setUserId(userId);
        shoppingCartItem.setId(shoppingCartItemId);
        return shoppingCartService.updateShoppingCartItem(shoppingCartItem);
    }

    @ApiOperation(value = "Remove a shopping cart item by id for a user", response = User.class)
    @DeleteMapping(EndPoints.User.USER_CART_ITEM_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void removeFromShoppingCart(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId,
                                       @PathVariable(value = EndPoints.PathVariable.SHOPPING_CART_ITEM_ID) Integer shoppingCartItemId) {
        shoppingCartService.removeFromShoppingCartItems(userId, shoppingCartItemId);
    }


    @ApiOperation(value = "Clear the whole cart list for user")
    @DeleteMapping(EndPoints.User.USER_CART)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearAllShoppingCartListForUser(@PathVariable(value = EndPoints.PathVariable.USER_ID) Integer userId) {
        shoppingCartService.clearAllShoppingCartListItemsForUserId(userId);
    }


}
