/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.Controller;

import io.swagger.annotations.ApiOperation;
import org.mihy.gowma.constants.EndPoints;
import org.mihy.gowma.model.ProductCategory;
import org.mihy.gowma.model.search.ProductCategorySearchRequest;
import org.mihy.gowma.service.ProductCategoryService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProductCategoryController extends AbstractWebResponseController  {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation(value = "Get a list of product categories for a parent category id if not provided will return all root levels")
    @GetMapping(EndPoints.ProductCategory.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductCategory> getProductCategoriesForParent(@RequestParam(value = EndPoints.QueryParam.PARENT_CATEGORY_ID, required = false)
                                                                       Optional<Integer> parentCategoryId) {
        return productCategoryService.getProductCategoriesForParentId(parentCategoryId);
    }

    @ApiOperation(value = "Create a product category")
    @PostMapping(EndPoints.ProductCategory.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCategory createProductCategory(@RequestBody ProductCategory productCategory) {
        return productCategoryService.create(productCategory);
    }

    @ApiOperation(value = "Update a product category by id")
    @PutMapping(EndPoints.ProductCategory.PRODUCT_CATEGORY_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProductCategory updateProductCategory(@PathVariable(EndPoints.PathVariable.PRODUCT_CATEGORY_ID) Integer productCategoryId,
                                                 @RequestBody ProductCategory productCategory) {
        productCategory.setId(productCategoryId);
        return productCategoryService.update(productCategory);
    }

    @ApiOperation(value = "Delete a product with category id")
    @DeleteMapping(EndPoints.ProductCategory.PRODUCT_CATEGORY_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductCategory(@PathVariable(EndPoints.PathVariable.PRODUCT_CATEGORY_ID) Integer productCategoryId) {
        productCategoryService.delete(productCategoryId);
    }

    @ApiOperation(value = "Performs search on product categories ")
    @PostMapping(EndPoints.ProductCategory.PRODUCT_CATEGORY_SEARCH)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductCategory> searchProductCategories(@RequestBody ProductCategorySearchRequest productCategorySearchRequest) {
        return productCategoryService.searchProductCategories(productCategorySearchRequest);
    }

}
