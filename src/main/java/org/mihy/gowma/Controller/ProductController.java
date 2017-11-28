/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.Controller;

import io.swagger.annotations.ApiOperation;
import org.mihy.gowma.constants.EndPoints;
import org.mihy.gowma.model.Product;
import org.mihy.gowma.model.ProductImage;
import org.mihy.gowma.model.ProductInventory;
import org.mihy.gowma.model.User;
import org.mihy.gowma.model.search.ProductSearchRequest;
import org.mihy.gowma.service.ProductImageService;
import org.mihy.gowma.service.ProductInventoryService;
import org.mihy.gowma.service.ProductService;
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
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductInventoryService productInventoryService;


    @ApiOperation(value = "Get a product by id")
    @GetMapping(EndPoints.Product.PRODUCT_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId) {
        return productService.get(productId);
    }

    @ApiOperation(value = "Create a product")
    @PostMapping(EndPoints.Product.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productService.create(product);
    }

    @ApiOperation(value = "Update a product with given id")
    @PutMapping(EndPoints.Product.PRODUCT_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId,
                                 @RequestBody Product product) {
        product.setId(productId);
        return productService.update(product);
    }

    @ApiOperation(value = "Delete a product with id")
    @DeleteMapping(EndPoints.Product.PRODUCT_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId) {
        productService.delete(productId);
    }

    @ApiOperation(value = "Performs search on products")
    @PostMapping(EndPoints.Product.PRODUCT_SEARCH)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Product> searchProducts(@RequestBody ProductSearchRequest productSearchRequest) {
        return productService.searchProducts(productSearchRequest);
    }

    //Product Images
    @ApiOperation(value = "Get a list of product images for a product with id")
    @GetMapping(EndPoints.Product.PRODUCT_IMAGES)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductImage> getAllProductImagesByProductId(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId) {
        return productImageService.getAllProductImagesByProductId(productId);
    }

    @ApiOperation(value = "Create a list of Product Images for a product with id")
    @PostMapping(EndPoints.Product.PRODUCT_IMAGES)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductImage> createProductImagesForProductId(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId,
                                                              @RequestBody List<ProductImage> productImages) {
        productImages.forEach(productImage -> productImage.setProductId(productId));
        return productImageService.createForProductId(productId, productImages);
    }

    @ApiOperation(value = "Update a product by product image id for a product with id")
    @PutMapping(EndPoints.Product.PRODUCT_IMAGE_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProductImage updateProductImageByIdForProductId(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId,
                                                           @PathVariable(EndPoints.PathVariable.PRODUCT_IMAGE_ID) Integer productImageId,
                                                           @RequestBody ProductImage productImage) {
        productImage.setId(productImageId);
        productImage.setProductId(productId);
        return productImageService.update(productImage);
    }

    @ApiOperation(value = "Delete a product image by id for a product id", response = User.class)
    @DeleteMapping(EndPoints.Product.PRODUCT_IMAGE_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductImageById(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId,
                                       @PathVariable(EndPoints.PathVariable.PRODUCT_IMAGE_ID) Integer productImageId) {
        productImageService.deleteByProductIdNId(productId, productImageId);
    }


    //Product Inventory

    @ApiOperation(value = "Get a product inventory for a product with id")
    @GetMapping(EndPoints.Product.PRODUCT_INVENTORY)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProductInventory getProductInventoryForProductId(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId) {
        return productInventoryService.getProductInventoryForProductId(productId);
    }

    @ApiOperation(value = "Create a list of Product Images for a product with id")
    @PostMapping(EndPoints.Product.PRODUCT_INVENTORY)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ProductInventory createProductInventoryForProductId(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId,
                                                               @RequestBody ProductInventory productInventory) {
        productInventory.setProductId(productId);
        return productInventoryService.createForProductId(productInventory);
    }

    @ApiOperation(value = "Update a product inventory product for a product with id")
    @PutMapping(EndPoints.Product.PRODUCT_INVENTORY_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProductInventory updateProductInventoryForProductId(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId,
                                                               @PathVariable(EndPoints.PathVariable.PRODUCT_INVENTORY_ID) Integer productInventoryId,
                                                               @RequestBody ProductInventory productInventory) {
        productInventory.setId(productInventoryId);
        productInventory.setProductId(productId);
        return productInventoryService.update(productInventory);
    }

    @ApiOperation(value = "Delete a product inventory for a product id", response = User.class)
    @DeleteMapping(EndPoints.Product.PRODUCT_INVENTORY_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductInventoryByIdNProductId(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId,
                                                     @PathVariable(EndPoints.PathVariable.PRODUCT_INVENTORY_ID) Integer productInventoryId) {
        productInventoryService.deleteByProductIdNId(productId, productInventoryId);
    }


}
