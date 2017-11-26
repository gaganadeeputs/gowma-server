/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository.querybuilder;

import org.mihy.gowma.model.search.BaseSearchRequest;
import org.mihy.gowma.model.search.ProductCategorySearchRequest;
import org.mihy.gowma.model.search.ProductSearchRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductQueryBuilder {


    private static final String BASE_SELECT_SQL = "SELECT * from product p";

    private static final Double DEFAULT_LOWER_PRICE = 0.0;
    private static final Double DEFAULT_HIGHER_PRICE = 99999999999999.0;


    public String buildSearchQuery(ProductSearchRequest productSearchRequest, final List preparedStatementValues) {
        final StringBuilder searchQuery = new StringBuilder(BASE_SELECT_SQL);

        boolean isWhereClauseRequired = true;
        boolean isAndClauseRequired = false;
        if(productSearchRequest.getInventoryStatus().isPresent()){
            searchQuery.append(" INNER JOIN product_inventory pi on pi.inventory__product_id=p.id");
            String fieldLevelQueryClause = getFieldEqualsQueryClause("pi.inventory__inventory_status", isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            preparedStatementValues.add(productSearchRequest.getInventoryStatus().get());
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productSearchRequest.getName().isPresent()) {
            String fieldLevelQueryClause = getFieldLikeQueryClause("p.product__name", isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            preparedStatementValues.add(productSearchRequest.getName().get());
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productSearchRequest.getCategoryId().isPresent()) {
            String fieldLevelQueryClause = getFieldEqualsQueryClause("p.product__category_id", isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            preparedStatementValues.add(productSearchRequest.getCategoryId().get());
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productSearchRequest.getIsActive().isPresent()) {
            String fieldLevelQueryClause = getFieldEqualsQueryClause("p.product__is_active", isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            preparedStatementValues.add(productSearchRequest.getIsActive().get());
            searchQuery.append(fieldLevelQueryClause);
        }
        if (productSearchRequest.getLowerPrice().isPresent() || productSearchRequest.getHigherPrice().isPresent() ) {
            Double lowerPrice = productSearchRequest.getLowerPrice().isPresent() ? productSearchRequest.getLowerPrice().get() : DEFAULT_LOWER_PRICE;
            Double higherPrice = productSearchRequest.getHigherPrice().isPresent() ? productSearchRequest.getHigherPrice().get() : DEFAULT_HIGHER_PRICE;
            String fieldLevelQueryClause = getFieldEqualsQueryClause("p.product__price", isWhereClauseRequired, isAndClauseRequired);
            preparedStatementValues.add(lowerPrice);
            preparedStatementValues.add(higherPrice);
            searchQuery.append(fieldLevelQueryClause);
        }

        searchQuery.append(buildOrderByClause(productSearchRequest));
        searchQuery.append(buildPaginationClause(productSearchRequest,preparedStatementValues));
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

    private String getFieldEqualsQueryClause(String fieldName, boolean isWhereClauseRequired, boolean isAndClauseRequired) {

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

    private String getFieldLikeQueryClause(String fieldName, boolean isWhereClauseRequired, boolean isAndClauseRequired) {

        StringBuilder queryExpression = new StringBuilder();
        queryExpression.append(fieldName + "'like % ? %'");
        if (isWhereClauseRequired) {
            queryExpression.insert(0, " WHERE ");
        }
        if (isAndClauseRequired) {
            queryExpression.insert(0, " AND ");
        }
        return queryExpression.toString();

    }

    private String getFieldsGreaterThanOrEqualQueryClause(String fieldName, boolean isWhereClauseRequired, boolean isAndClauseRequired) {

        StringBuilder queryExpression = new StringBuilder();
        queryExpression.append(fieldName + ">=?");
        if (isWhereClauseRequired) {
            queryExpression.insert(0, " WHERE ");
        }
        if (isAndClauseRequired) {
            queryExpression.insert(0, " AND ");
        }
        return queryExpression.toString();

    }

    private String getFieldsBetweenQueryClause(String fieldName, boolean isWhereClauseRequired, boolean isAndClauseRequired) {

        StringBuilder queryExpression = new StringBuilder();
        queryExpression.append(fieldName + "BETWEEN ? AND ?");
        if (isWhereClauseRequired) {
            queryExpression.insert(0, " WHERE ");
        }
        if (isAndClauseRequired) {
            queryExpression.insert(0, " AND ");
        }
        return queryExpression.toString();

    }
}
