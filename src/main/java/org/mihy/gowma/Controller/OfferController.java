/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.Controller;

import io.swagger.annotations.ApiOperation;
import org.mihy.gowma.constants.EndPoints;
import org.mihy.gowma.model.Offer;
import org.mihy.gowma.model.User;
import org.mihy.gowma.service.OfferService;
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
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class OfferController extends AbstractWebResponseController  {

    @Autowired
    private OfferService offerService;

  /*  @ApiOperation(value = "Get a list of offers for category and product id")
    @GetMapping(EndPoints.Offer.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Offer> getOffersForCategoryIdNProductId(@RequestParam(value = EndPoints.QueryParam.OFFER_CATEGORY_ID) Integer categoryId,
                                                        @RequestParam(value = EndPoints.QueryParam.OFFER_PRODUCT_ID, required = false) Integer productId) {

        return offerService.getOffersForCategoryIdNProductId(categoryId, productId);
    }
*/
    @ApiOperation(value = "Create a offer")
    @PostMapping(EndPoints.Offer.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Offer createProduct(@RequestBody Offer offer) {
        return offerService.create(offer);
    }

    @ApiOperation(value = "Update a offer by id")
    @PutMapping(EndPoints.Offer.OFFER_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Offer updateProduct(@PathVariable(EndPoints.PathVariable.OFFER_ID) Integer offerId,
                               @RequestBody Offer offer) {
        return offerService.update(offerId, offer);
    }

    @ApiOperation(value = "Delete a offer by id", response = User.class)
    @DeleteMapping(EndPoints.Offer.OFFER_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(EndPoints.PathVariable.OFFER_ID) Integer offerId) {
        offerService.delete(offerId);
    }


}
