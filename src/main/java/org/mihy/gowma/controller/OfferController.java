/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.controller;

import io.swagger.annotations.ApiOperation;
import org.mihy.gowma.constants.EndPoints;
import org.mihy.gowma.model.Offer;
import org.mihy.gowma.model.OfferMapping;
import org.mihy.gowma.model.search.OfferSearchRequest;
import org.mihy.gowma.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class OfferController extends AbstractWebResponseController {

    @Autowired
    private OfferService offerService;

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
        offer.setId(offerId);
        return offerService.update(offer);
    }

    @ApiOperation(value = "Delete a offer by id")
    @DeleteMapping(EndPoints.Offer.OFFER_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(EndPoints.PathVariable.OFFER_ID) Integer offerId) {
        offerService.delete(offerId);
    }

    @ApiOperation(value = "Assign a offer by id to either category or product id")
    @PostMapping(EndPoints.Offer.OFFER_ASSIGN)
    @ResponseStatus(HttpStatus.CREATED)
    public OfferMapping assignOffer(@RequestBody OfferMapping offerMapping) {
        return offerService.assignOffer(offerMapping);
    }

    @ApiOperation(value = "UnAssign a offer by id to either category or product id")
    @DeleteMapping(EndPoints.Offer.OFFER_UNASSIGN)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unAssignOffer(@RequestBody OfferMapping offerMapping) {
        offerService.unAssignOffer(offerMapping);
    }

    @ApiOperation(value = "UnAssign a offer by id to either category or product id")
    @PostMapping(EndPoints.Offer.OFFER_SEARCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public List<Offer> performSearch(@RequestBody OfferSearchRequest offerSearchRequest) {
        return offerService.searchOffers(offerSearchRequest);
    }

}
