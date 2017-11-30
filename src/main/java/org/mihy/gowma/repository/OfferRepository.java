/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.model.Offer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class OfferRepository extends BaseRepository {

    public static String SELECT_ALL = "SELECT * from offer";

    public static String INSERT_SQL = "INSERT INTO offer (offer__name,offer__percenatge,offer__valid_from,offer__valid_to)" +
            " VALUES(:name,:percentage,:validFrom,:validTo)";

    public static String UPDATE_BY_ID_SQL = "UPDATE offer SET offer__name=:name" +
            "offer__percenatge=:offer__percenatge" +
            "offer__valid_from=:validFrom" +
            "offer__valid_to=:validTo";

    public static String SOFT_DELETE_BY_ID_SQL = "UPDATE offer SET offer__is_deleted=true WHERE id=:id";

    public Offer create(Offer offer) {
        super.insert(offer, INSERT_SQL, new EnumBeanPropParamSource(offer));
        return offer;
    }

    public Offer update(Offer offer) {
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(offer);
        namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, paramSource);
        return offer;
    }

    public void delete(Integer offerId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", offerId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID_SQL, params);

    }
}
