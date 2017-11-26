/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository.querybuilder;

import org.mihy.gowma.model.search.BaseSearchRequest;
import org.mihy.gowma.model.search.ProductCategorySearchRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductCategoryQueryBuilder {


    private static final String BASE_SELECT_SQL = "SELECT * from product_category pc ";

    public String buildSearchQuery(ProductCategorySearchRequest productCategorySearchRequest, final List preparedStatementValues) {
        final StringBuilder searchQuery = new StringBuilder(BASE_SELECT_SQL);

        boolean isWhereClauseRequired = true;
        boolean isAndClauseRequired = false;
        if (productCategorySearchRequest.getName().isPresent()) {
            String fieldLevelQueryClause = getFieldQueryClause("pc.product_category__name", isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            preparedStatementValues.add(productCategorySearchRequest.getName().get());
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productCategorySearchRequest.getParentCategoryId().isPresent()) {
            String fieldLevelQueryClause = getFieldQueryClause("pc.product_category__parent_id=", isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            preparedStatementValues.add(productCategorySearchRequest.getParentCategoryId().get());
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productCategorySearchRequest.getEnabled().isPresent()) {
            String fieldLevelQueryClause = getFieldQueryClause("pc.product_category__parent_id=", isWhereClauseRequired, isAndClauseRequired);
            preparedStatementValues.add(productCategorySearchRequest.getEnabled().get());
            searchQuery.append(fieldLevelQueryClause);
        }

        searchQuery.append(buildOrderByClause(productCategorySearchRequest));
        searchQuery.append(buildPaginationClause(productCategorySearchRequest,preparedStatementValues));
        return searchQuery.toString();
    }

    private String buildOrderByClause(BaseSearchRequest searchRequest) {
        StringBuilder orderByClause = new StringBuilder("ORDER BY ");
        if(searchRequest.getSorts().isPresent()) {
            searchRequest.getSorts().get().forEach(sortField -> orderByClause.append("pc.").
                     append(sortField.getName())
                    .append(sortField.getSortOrder()));

        }
        return  orderByClause.toString();
    }

    private String buildPaginationClause(BaseSearchRequest searchRequest, List preparedStatementValues) {
        String paginationClause = "OFFSET ? LIMIT ?";
        int pageNumber = searchRequest.getPageNumber().isPresent() ? searchRequest.getPageNumber().get() : 0;
        int pageSize = searchRequest.getPageSize().isPresent() ? searchRequest.getPageSize().get() : 20;
        preparedStatementValues.add(pageNumber);
        preparedStatementValues.add(pageSize);
        return paginationClause;
    }

    private String getFieldQueryClause(String fieldName, boolean isWhereClauseRequired, boolean isAndClauseRequired) {

        StringBuilder queryExpression = new StringBuilder();
        queryExpression.append(fieldName + "=?");
        if (isWhereClauseRequired) {
            queryExpression.insert(0, " WHERE ");
        }
        if (isAndClauseRequired) {
            queryExpression.insert(0, " AND ");
        }
        return queryExpression.toString();


    }
}
