/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository.querybuilder;

import org.mihy.gowma.model.search.OrderSearchRequest;
import org.mihy.gowma.utilities.QueryBuildingUtilities;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderQueryBuilder {


    private static final String BASE_SELECT_SQL = "SELECT * from gowma_order go ";
    private static final LocalDateTime DEFAULT_START_DATE = LocalDateTime.now();
    private static final LocalDateTime DEFAULT_END_DATE = LocalDateTime.now();

    public String buildSearchQuery(OrderSearchRequest orderSearchRequest, final List preparedStatementValues) {
        final StringBuilder searchQuery = new StringBuilder(BASE_SELECT_SQL);

        boolean isWhereClauseRequired = true;
        boolean isAndClauseRequired = false;

        if (orderSearchRequest.getShippingStatus() != null) {
            searchQuery.append(" INNER JOIN order_shipping_detail osd ON osd.order_shipping_detail__order_txn_id=go.id ");
            String fieldLevelQueryClause = QueryBuildingUtilities.getEnumFieldEqualsQueryClause("osd.order_shipping_detail__status", "shipping_status", orderSearchRequest.getShippingStatus().name(), isWhereClauseRequired, isAndClauseRequired);
            searchQuery.append(fieldLevelQueryClause);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
        }
        if (orderSearchRequest.getUserId() != null) {
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldEqualsQueryClause("go.gowma_order__user_id",isWhereClauseRequired, isAndClauseRequired);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
            preparedStatementValues.add(orderSearchRequest.getUserId());
            searchQuery.append(fieldLevelQueryClause);
        }

        if (orderSearchRequest.getOrderStatus() != null) {
            String fieldLevelQueryClause = QueryBuildingUtilities.getEnumFieldEqualsQueryClause("go.gowma_order__status", "order_status", orderSearchRequest.getShippingStatus().name(), isWhereClauseRequired, isAndClauseRequired);
            searchQuery.append(fieldLevelQueryClause);
            isWhereClauseRequired = false;
            isAndClauseRequired = true;
        }
        if (orderSearchRequest.getStartOrderDate() != null || orderSearchRequest.getEndOrderDate() != null) {
            LocalDateTime startDate = orderSearchRequest.getStartOrderDate() != null ? orderSearchRequest.getStartOrderDate() : DEFAULT_START_DATE;
            LocalDateTime endDate = orderSearchRequest.getEndOrderDate() != null ? orderSearchRequest.getEndOrderDate() : DEFAULT_END_DATE;
            String fieldLevelQueryClause = QueryBuildingUtilities.getFieldsBetweenQueryClause("go.gowma_order__date", isWhereClauseRequired, isAndClauseRequired);
            preparedStatementValues.add(startDate);
            preparedStatementValues.add(endDate);
            searchQuery.append(fieldLevelQueryClause);
        }


        String fieldLevelQueryClause = QueryBuildingUtilities.getFieldEqualsQueryClause("o.offer_mapping__is_deleted", isWhereClauseRequired, isAndClauseRequired);
        preparedStatementValues.add(false);
        searchQuery.append(fieldLevelQueryClause);

        searchQuery.append(QueryBuildingUtilities.buildOrderByClause(orderSearchRequest, "o.id"));
        searchQuery.append(QueryBuildingUtilities.buildPaginationClause(orderSearchRequest, preparedStatementValues));
        return searchQuery.toString();
    }


}
