/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.AuthenticatedUser;
import org.mihy.gowma.model.Offer;
import org.mihy.gowma.model.OfferMapping;
import org.mihy.gowma.model.search.OfferSearchRequest;
import org.mihy.gowma.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public Offer create(Offer offer) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        offer.setCreatedBy(authenticatedUser.getId());
        offer.setCreatedDate(LocalDateTime.now());
        return offerRepository.create(offer);
    }

    public Offer update( Offer offer) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        offer.setCreatedBy(authenticatedUser.getId());
        offer.setCreatedDate(LocalDateTime.now());
        return offerRepository.update(offer);
    }

    public void delete(Integer offerId) {
        offerRepository.delete(offerId);
    }


    public OfferMapping assignOffer(OfferMapping offerMapping) {
        return offerRepository.assignOffer(offerMapping);
    }

    public void unAssignOffer(OfferMapping offerMapping) {
        offerRepository.unAssignOffer(offerMapping);
    }

    public List<Offer> searchOffers(OfferSearchRequest offerSearchRequest) {
        return offerRepository.findAll(offerSearchRequest);
    }
}
