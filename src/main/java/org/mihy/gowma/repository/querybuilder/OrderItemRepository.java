package org.mihy.gowma.repository.querybuilder;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.exception.GowmaServiceExceptionCode;
import org.mihy.gowma.exception.GowmaServiceRuntimeException;
import org.mihy.gowma.model.OrderItem;
import org.mihy.gowma.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItemRepository extends BaseRepository {


    private static final String SELECT_BY_ID_SQL = "";
    @Autowired

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


    public OrderItem update(OrderItem orderItem) {
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(orderItem);
        namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, paramSource);
        return orderItem;
    }

    public void delete(Integer orderId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", orderId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID_SQL, params);
    }


    public OrderItem getById(Integer orderId) {
        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("id", orderId);
        List<OrderItem> orderItems = namedParameterJdbcTemplate.query(SELECT_BY_ID_SQL, paramNameToValuesMap, new OrderItemRowMapper());
        if (orderItems.isEmpty())
            throw new GowmaServiceRuntimeException(GowmaServiceExceptionCode.CFG_GENERIC_INVALID_ID, "Invalid Order Item ID");
        OrderItem order = orderItems.get(0);

        return order;
    }

    public List<OrderItem> create(List<OrderItem> orderItems) {
        orderItems.stream().forEach(orderItem -> create(orderItem));
        return orderItems;
    }

    public OrderItem create(OrderItem orderItem) {
        super.insert(orderItem, INSERT_SQL, new EnumBeanPropParamSource(orderItem));
        return orderItem;
    }

    public List<OrderItem> getOrderItemsForOrderId(Integer orderId) {
        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("id", orderId);
        List<OrderItem> orderItems = namedParameterJdbcTemplate.query(SELECT_BY_ID_SQL, paramNameToValuesMap, new OrderItemRowMapper());
        return orderItems;
    }

    private class OrderItemRowMapper implements RowMapper<OrderItem> {

        @Override
        public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderItem orderItem = new OrderItem();


            return orderItem;
        }
    }
}
