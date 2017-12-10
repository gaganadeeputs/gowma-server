/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.model.Offer;
import org.mihy.gowma.model.OfferMapping;
import org.mihy.gowma.model.search.OfferSearchRequest;
import org.mihy.gowma.repository.querybuilder.OfferQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Repository
public class OfferRepository extends BaseRepository {

    //offer
    public static String INSERT_SQL = "INSERT INTO offer (offer__name,offer__percenatge,offer__valid_from,offer__valid_to)" +
            " VALUES(:name,:percentage,:validFrom,:validTo)";


    public static String UPDATE_BY_ID_SQL = "UPDATE offer SET offer__name=:name" +
            "offer__percenatge=:offer__percenatge" +
            "offer__valid_from=:validFrom" +
            "offer__valid_to=:validTo";

    public static String SOFT_DELETE_BY_ID_SQL = "UPDATE offer SET offer__is_deleted=true WHERE id=:id";


    //Offer Mapping

    public static String INSERT_INTO_OFFER_MAPPING_SQL = "INSERT INTO offer_mapping (offer_mapping__offer_id,offer_mapping__product_id," +
            "offer_mapping__category_id,offer_mapping__created_date,offer_mapping__created_by)" +
            " VALUES(:offerId,:productId,:categoryId,:createdDate,:createdBy)";

    public static String SOFT_DELETE_OFFER_MAPPING_BY_OFFER_ID_AND_PRODUCT_ID = "" +
            "UPDATE offer_mapping SET offer_mapping__is_deleted=true" +
            " WHERE offer_mapping__offer_id=:offerId AND offer_mapping__product_id=:productId";

    public static String SOFT_DELETE_OFFER_MAPPING_BY_OFFER_ID_AND_CATEGORY_ID = "" +
            "UPDATE offer_mapping SET offer_mapping__is_deleted=true" +
            " WHERE offer_mapping__offer_id=:offerId AND offer_mapping__category_id=:categoryId";

    @Autowired
    OfferQueryBuilder offerQueryBuilder;

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

    public void unAssignOffer(OfferMapping offerMapping) {
        String deleteQuery = SOFT_DELETE_OFFER_MAPPING_BY_OFFER_ID_AND_PRODUCT_ID;
        if (offerMapping.getCategoryId() != null) {
            deleteQuery = SOFT_DELETE_OFFER_MAPPING_BY_OFFER_ID_AND_CATEGORY_ID;
        }
        namedParameterJdbcTemplate.update(deleteQuery, new EnumBeanPropParamSource(offerMapping));
    }

    public OfferMapping assignOffer(OfferMapping offerMapping) {
        super.insert(offerMapping, INSERT_INTO_OFFER_MAPPING_SQL, new EnumBeanPropParamSource(offerMapping));
        return offerMapping;
    }

    public List<Offer> findAll(OfferSearchRequest offerSearchRequest) {
        List<Object> preparedStatementValues = new ArrayList<>();
        String searchQuery = offerQueryBuilder.buildSearchQuery(offerSearchRequest, preparedStatementValues);
        List<Offer> offers = namedParameterJdbcTemplate.getJdbcOperations().query(searchQuery, preparedStatementValues.toArray(), new OfferRowMapper());
        return offers;
    }

    private class OfferRowMapper implements RowMapper<Offer> {
        @Override
        public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Offer offer = new Offer();
            offer.setId(rs.getInt("id"));
            offer.setName(rs.getString("offer__name"));
            offer.setPercentage(rs.getDouble("offer__percenatge"));
            offer.setValidFrom(LocalDateTime.ofInstant(rs.getDate("offer__valid_from").toInstant(), ZoneId.systemDefault()));
            offer.setValidTo(LocalDateTime.ofInstant(rs.getDate("offer__valid_to").toInstant(), ZoneId.systemDefault()));
            return offer;
        }
    }
}
