package org.mihy.gowma.utilities;

import org.mihy.gowma.model.search.BaseSearchRequest;

import java.util.List;

public final class QueryBuildingUtilities {

    private QueryBuildingUtilities() {

    }

    public static String buildPaginationClause(BaseSearchRequest searchRequest, List preparedStatementValues) {
        String paginationClause = " OFFSET ? LIMIT ?";
        int pageNumber = searchRequest.getPageNumber() != null ? searchRequest.getPageNumber() - 1 : 0;
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        int pageSize = searchRequest.getPageSize() != null ? searchRequest.getPageSize() : 20;
        preparedStatementValues.add(pageNumber * pageSize);
        preparedStatementValues.add(pageSize);
        return paginationClause;
    }

    public static String getFieldEqualsQueryClause(String fieldName, boolean isWhereClauseRequired, boolean isAndClauseRequired) {

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

    public static String getFieldLikeQueryClause(String fieldName, String value, boolean isWhereClauseRequired, boolean isAndClauseRequired) {

        StringBuilder queryExpression = new StringBuilder();
        queryExpression.append(fieldName + " LIKE '%" + value.trim() + "%'");
        if (isWhereClauseRequired) {
            queryExpression.insert(0, " WHERE ");
        }
        if (isAndClauseRequired) {
            queryExpression.insert(0, " AND ");
        }
        return queryExpression.toString();

    }

    public static String getFieldsBetweenQueryClause(String fieldName, boolean isWhereClauseRequired, boolean isAndClauseRequired) {

        StringBuilder queryExpression = new StringBuilder();
        queryExpression.append(fieldName + " BETWEEN ? AND ?");
        if (isWhereClauseRequired) {
            queryExpression.insert(0, " WHERE ");
        }
        if (isAndClauseRequired) {
            queryExpression.insert(0, " AND ");
        }
        return queryExpression.toString();

    }

    public static String buildOrderByClause(BaseSearchRequest searchRequest, String IdColumnName) {
        StringBuilder orderByClause = new StringBuilder(" ORDER BY");
        orderByClause.append(" " + IdColumnName);
        orderByClause.append(" ASC");

        if (searchRequest.getSorts() != null) {
            searchRequest.getSorts().forEach(sortField -> orderByClause.append("pc.").
                    append(sortField.getName())
                    .append(sortField.getSortOrder()).append(","));

        }
        return orderByClause.toString();
    }

    public static String getEnumFieldEqualsQueryClause(String fieldName, String enumTypenameInDB, String value, boolean isWhereClauseRequired, boolean isAndClauseRequired) {
        StringBuilder queryExpression = new StringBuilder();
        queryExpression.append(fieldName + "=" + "'" + value + "'" + "::" + enumTypenameInDB);
        if (isWhereClauseRequired) {
            queryExpression.insert(0, " WHERE ");
        }
        if (isAndClauseRequired) {
            queryExpression.insert(0, " AND ");
        }
        return queryExpression.toString();
    }

    public static String getFieldsGreaterThanOrEqualQueryClause(String fieldName, boolean isWhereClauseRequired, boolean isAndClauseRequired) {

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


    public static String  getFieldsLessThanOrEqualQueryClause(String fieldName, boolean isWhereClauseRequired, boolean isAndClauseRequired) {

        StringBuilder queryExpression = new StringBuilder();
        queryExpression.append(fieldName + "<=?");
        if (isWhereClauseRequired) {
            queryExpression.insert(0, " WHERE ");
        }
        if (isAndClauseRequired) {
            queryExpression.insert(0, " AND ");
        }
        return queryExpression.toString();

    }
}
