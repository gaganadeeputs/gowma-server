/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository.querybuilder;

import org.mihy.gowma.model.search.OfferSearchRequest;
import org.mihy.gowma.utilities.QueryBuildingUtilities;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OfferQueryBuilder {


    private static final String BASE_SELECT_SQL = "SELECT * from offer o ";
    private static final Double DEFAULT_LOWER_PRICE = 0.0;
    private static final Double DEFAULT_HIGHER_PRICE = 99999999999.9999D;

    public String buildSearchQuery(OfferSearchRequest offerSearchRequest, final List preparedStatementValues) {
        final StringBuilder searchQuery = new StringBuilder(BASE_SELECT_SQL);

        boolean isWhereClauseRequired = true;
        boolean isAndClauseRequired = false;

        if (offerSearchRequest.getProductId() != null || offerSearchRequest.getCategoryId() != null) {
            searchQuery.append(" INNER JOIN offer_mapping om ON o.id=om.offer_mapping__offer_id ");
            if (offerSearchRequest.getProductId() != null) {
                String fieldLevelQueryClause = QueryBuildingUtilities.getFieldEqualsQueryClause("om.offer_mapping__category_id", isWhereClauseRequired, isAndClauseRequired);
                preparedStatementValues.add(offerSearchRequest.getCategoryId());
                searchQuery.append(fieldLevelQueryClause);
            } else {
                String fieldLevelQueryClause = QueryBuildingUtilities.getFieldEqualsQueryClause("om.offer_mapping__product_id", isWhereClauseRequired, isAndClauseRequired);
                preparedStatementValues.add(offerSearchRequest.getProductId());
                searchQuery.append(fieldLevelQueryClause);
            }
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
        }
        if (offerSearchRequest.getName() != null) {
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldLikeQueryClause("o.offer__name", offerSearchRequest.getName(), isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            searchQuery.append(fieldLevelQueryClause);
        }

        if (offerSearchRequest.getHigherPercentage() != null || offerSearchRequest.getHigherPercentage() != null) {
            Double lowerPercentage = offerSearchRequest.getLowerPercentage() != null ? offerSearchRequest.getLowerPercentage() : DEFAULT_LOWER_PRICE;
            Double higherPercentage = offerSearchRequest.getHigherPercentage() != null ? offerSearchRequest.getHigherPercentage() : DEFAULT_HIGHER_PRICE;
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldsBetweenQueryClause("o.offer__percenatge", isWhereClauseRequired, isAndClauseRequired);
            preparedStatementValues.add(lowerPercentage);
            preparedStatementValues.add(higherPercentage);
            searchQuery.append(fieldLevelQueryClause);
        }


        if (offerSearchRequest.getValidFrom() != null) {
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldsGreaterThanOrEqualQueryClause("o.offer__valid_from", isWhereClauseRequired, isAndClauseRequired);
            preparedStatementValues.add(offerSearchRequest.getValidFrom());
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            searchQuery.append(fieldLevelQueryClause);
        }

        if(offerSearchRequest.getValidTo() !=null){
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldsLessThanOrEqualQueryClause("o.offer__valid_to", isWhereClauseRequired, isAndClauseRequired);
            preparedStatementValues.add(offerSearchRequest.getValidFrom());
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            searchQuery.append(fieldLevelQueryClause);
        }

        String fieldLevelQueryClause = QueryBuildingUtilities.getFieldEqualsQueryClause("o.offer_mapping__is_deleted", isWhereClauseRequired, isAndClauseRequired);
        preparedStatementValues.add(false);
        searchQuery.append(fieldLevelQueryClause);

        searchQuery.append(QueryBuildingUtilities.buildOrderByClause(offerSearchRequest, "o.offer__valid_from"));
        searchQuery.append(QueryBuildingUtilities.buildPaginationClause(offerSearchRequest, preparedStatementValues));
        return searchQuery.toString();
    }


}
