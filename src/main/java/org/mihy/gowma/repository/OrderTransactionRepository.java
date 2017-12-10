package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.exception.GowmaServiceExceptionCode;
import org.mihy.gowma.exception.GowmaServiceRuntimeException;
import org.mihy.gowma.model.OrderTransaction;
import org.mihy.gowma.repository.querybuilder.OrderItemRepository;
import org.mihy.gowma.repository.querybuilder.OrderQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderTransactionRepository extends BaseRepository {


    private static final String SELECT_BY_ID_SQL = "";
    private final String INSERT_SQL = "INSERT INTO gowma_order(gowma_order__no," +
            "gowma_order__date,gowma_order__status,gowma_order__user_id,gowma_order__user_address_id,gowma_order__created_date,gowma_order__created_by" +
            ") VALUES(:orderNo,:orderedDate,:status::order_status,:user.id,:createdDate,:createdBy)";

    private final String UPDATE_BY_ID_SQL = "UPDATE gowma_order " +
            "SET gowma_order__status=:status::order_status," +
            "gowma_order__last_modified_date=:lastModifiedDate," +
            "gowma_order__last_modified_by=:lastModifiedBy" +
            "WHERE id=:id";

    private final String SOFT_DELETE_BY_ID_SQL = "UPDATE gowma_order " +
            "SET gowma_order__is_deleted=true" +
            "WHERE id=:id";
    @Autowired
    private OrderQueryBuilder orderQueryBuilder;

    @Autowired
    private OrderItemRepository orderItemRepository;


    public OrderTransaction update(OrderTransaction orderTransaction) {
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(orderTransaction);
        namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, paramSource);
        return orderTransaction;
    }

    public void delete(Integer orderId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", orderId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID_SQL, params);
    }


    public OrderTransaction getById(Integer orderId) {
        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("id", orderId);
        List<OrderTransaction> orderTransactions = namedParameterJdbcTemplate.query(SELECT_BY_ID_SQL, paramNameToValuesMap, new OrderTransactionRowMapper());
        if (orderTransactions.isEmpty())
            throw new GowmaServiceRuntimeException(GowmaServiceExceptionCode.CFG_GENERIC_INVALID_ID, "Invalid order ID");
        OrderTransaction orderTransaction = orderTransactions.get(0);

        return orderTransaction;
    }

    public OrderTransaction create(OrderTransaction orderTransaction) {
        super.insert(orderTransaction, INSERT_SQL, new EnumBeanPropParamSource(orderTransaction));
        return orderTransaction;
    }

    public OrderTransaction getOrderTransactionForOrderId(Integer orderId) {
        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("id", orderId);
        List<OrderTransaction> orderTransactions = namedParameterJdbcTemplate.query(SELECT_BY_ID_SQL, paramNameToValuesMap, new OrderTransactionRowMapper());
        if (orderTransactions.isEmpty())
            throw new GowmaServiceRuntimeException(GowmaServiceExceptionCode.CFG_GENERIC_INVALID_ID, "Invalid order ID");
        OrderTransaction orderTransaction = orderTransactions.get(0);

        return orderTransaction;
    }

    private class OrderTransactionRowMapper implements RowMapper<OrderTransaction> {

        @Override
        public OrderTransaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderTransaction orderTransaction = new OrderTransaction();


            return orderTransaction;
        }
    }
}
