/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.controller;

import io.swagger.annotations.ApiOperation;
import org.mihy.gowma.constants.EndPoints;
import org.mihy.gowma.model.TaxDetail;
import org.mihy.gowma.service.TaxDetailService;
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

@RestController
@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class TaxDetailController extends AbstractWebResponseController  {

    @Autowired
    private TaxDetailService taxDetailService;

    @ApiOperation(value = "Get a list of tax details for category and offer id")
    @GetMapping(EndPoints.TaxDetail.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<TaxDetail> getTaxDetailsForCategoryIdNProductId(@RequestParam(value = EndPoints.QueryParam.OFFER_CATEGORY_ID) Integer categoryId,
                                                                @RequestParam(value = EndPoints.QueryParam.OFFER_PRODUCT_ID, required = false) Integer productId) {

        return taxDetailService.getTaxDetailsForCategoryIdRProductId(categoryId, productId);
    }

    @ApiOperation(value = "Create a tax detail")
    @PostMapping(EndPoints.TaxDetail.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public TaxDetail createTaxDetail(@RequestBody TaxDetail taxDetail) {
        return taxDetailService.create(taxDetail);
    }


    @ApiOperation(value = "Update a tax detail by id")
    @PutMapping(EndPoints.TaxDetail.TAX_DETAIL_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TaxDetail updateTaxDetail(@PathVariable(EndPoints.PathVariable.TAX_DETAIL_ID) Integer taxDetailId,
                                     @RequestBody TaxDetail taxDetail) {
        taxDetail.setId((taxDetailId));
        return taxDetailService.update(taxDetailId, taxDetail);
    }


    @ApiOperation(value = "Delete a tax detail by id")
    @DeleteMapping(EndPoints.TaxDetail.TAX_DETAIL_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaxDetail(@PathVariable(EndPoints.PathVariable.TAX_DETAIL_ID) Integer taxDetailId) {
        taxDetailService.delete(taxDetailId);
    }


}
