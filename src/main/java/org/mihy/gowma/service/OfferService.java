/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.Offer;
import org.mihy.gowma.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

   /* public List<Offer> getOffersForCategoryIdNProductId(Integer categoryId, Integer productId) {
        return offerRepository.getOffersForCategoryIdNProductId(categoryId, productId);
    }*/

    public Offer create(Offer offer) {

        return offerRepository.create(offer);
    }

    public Offer update(Integer offerId, Offer offer) {
        return offerRepository.update(offer);
    }

    public void delete(Integer offerId) {
        offerRepository.delete(offerId);
    }
}
