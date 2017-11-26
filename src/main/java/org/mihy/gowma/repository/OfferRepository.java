/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.model.Offer;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class OfferRepository extends BaseRepository {

    public List<Offer> getOffersForCategoryIdNProductId(Integer categoryId, Integer productId) {
        return null;
    }

    public Offer create(Offer offer) {
        return null;
    }

    public Offer update(Offer offer) {
        return null;
    }

    public void delete(Integer offerId) {

    }
}
