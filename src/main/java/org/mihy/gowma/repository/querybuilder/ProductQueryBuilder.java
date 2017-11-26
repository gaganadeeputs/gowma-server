/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository.querybuilder;

import org.mihy.gowma.model.search.BaseSearchRequest;
import org.mihy.gowma.model.search.ProductSearchRequest;
import org.mihy.gowma.utilities.QueryBuildingUtilities;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductQueryBuilder {


    private static final String BASE_SELECT_SQL = "SELECT * from product p INNER JOIN unit_of_measure uof ON uof.id=p.product__unit_of_measure_id ";

    private static final Double DEFAULT_LOWER_PRICE = 0.0;
    private static final Double DEFAULT_HIGHER_PRICE = 99999999999999.0;


    public String buildSearchQuery(ProductSearchRequest productSearchRequest, final List preparedStatementValues) {
        final StringBuilder searchQuery = new StringBuilder(BASE_SELECT_SQL);

        boolean isWhereClauseRequired = true;
        boolean isAndClauseRequired = false;
        if(productSearchRequest.getInventoryStatus()!=null){
            searchQuery.append(" INNER JOIN product_inventory pi on pi.inventory__product_id=p.id");
            String fieldLevelQueryClause = QueryBuildingUtilities.getEnumFieldEqualsQueryClause("pi.inventory__inventory_status","inventory_status",productSearchRequest.getInventoryStatus().name(), isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productSearchRequest.getName()!=null) {
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldLikeQueryClause("p.product__name", productSearchRequest.getName(), isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productSearchRequest.getCategoryId()!=null) {
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldEqualsQueryClause("p.product__category_id", isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            preparedStatementValues.add(productSearchRequest.getCategoryId());
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productSearchRequest.getIsActive()!=null) {
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldEqualsQueryClause("p.product__is_active", isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            preparedStatementValues.add(productSearchRequest.getIsActive());
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productSearchRequest.getLowerPrice()!=null || productSearchRequest.getHigherPrice()!=null ) {
            Double lowerPrice = productSearchRequest.getLowerPrice()!=null ? productSearchRequest.getLowerPrice() : DEFAULT_LOWER_PRICE;
            Double higherPrice = productSearchRequest.getHigherPrice()!=null ? productSearchRequest.getHigherPrice() : DEFAULT_HIGHER_PRICE;
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldsBetweenQueryClause("p.product__price", isWhereClauseRequired, isAndClauseRequired);
            preparedStatementValues.add(lowerPrice);
            preparedStatementValues.add(higherPrice);
            searchQuery.append(fieldLevelQueryClause);
        }

        searchQuery.append(QueryBuildingUtilities.buildOrderByClause(productSearchRequest,"p.id"));
        searchQuery.append(QueryBuildingUtilities.buildPaginationClause(productSearchRequest,preparedStatementValues));
        return searchQuery.toString();
    }




}
