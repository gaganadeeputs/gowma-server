/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository.querybuilder;

import org.mihy.gowma.model.search.BaseSearchRequest;
import org.mihy.gowma.model.search.ProductCategorySearchRequest;
import org.mihy.gowma.utilities.QueryBuildingUtilities;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductCategoryQueryBuilder {


    private static final String BASE_SELECT_SQL = "SELECT * from product_category pc ";

    public String buildSearchQuery(ProductCategorySearchRequest productCategorySearchRequest, final List preparedStatementValues) {
        final StringBuilder searchQuery = new StringBuilder(BASE_SELECT_SQL);

        boolean isWhereClauseRequired = true;
        boolean isAndClauseRequired = false;
        if (productCategorySearchRequest.getName() != null) {
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldLikeQueryClause("pc.product_category__name",productCategorySearchRequest.getName(), isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productCategorySearchRequest.getParentCategoryId()  != null) {
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldEqualsQueryClause("pc.product_category__parent_id", isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            preparedStatementValues.add(productCategorySearchRequest.getParentCategoryId());
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productCategorySearchRequest.getEnabled() != null) {
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldEqualsQueryClause("pc.product_category__enabled", isWhereClauseRequired, isAndClauseRequired);
            preparedStatementValues.add(productCategorySearchRequest.getEnabled());
            searchQuery.append(fieldLevelQueryClause);
        }

        searchQuery.append(QueryBuildingUtilities.buildOrderByClause(productCategorySearchRequest,"pc.id"));
        searchQuery.append(QueryBuildingUtilities.buildPaginationClause(productCategorySearchRequest,preparedStatementValues));
        return searchQuery.toString();
    }




}
