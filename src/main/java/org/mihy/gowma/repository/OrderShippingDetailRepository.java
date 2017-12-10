package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.exception.GowmaServiceExceptionCode;
import org.mihy.gowma.exception.GowmaServiceRuntimeException;
import org.mihy.gowma.model.OrderShippingDetail;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderShippingDetailRepository extends BaseRepository {


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


    public OrderShippingDetail update(OrderShippingDetail orderShippingDetail) {
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(orderShippingDetail);
        namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, paramSource);
        return orderShippingDetail;
    }

    public void delete(Integer orderShippingDetailId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", orderShippingDetailId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID_SQL, params);
    }


    public OrderShippingDetail create(OrderShippingDetail orderShippingDetail) {
        super.insert(orderShippingDetail, INSERT_SQL, new EnumBeanPropParamSource(orderShippingDetail));
        return orderShippingDetail;
    }

    public OrderShippingDetail getOrderShippingDetailForOrderId(Integer orderId) {
        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("id", orderId);
        List<OrderShippingDetail> orderShippingDetails = namedParameterJdbcTemplate.query(SELECT_BY_ID_SQL, paramNameToValuesMap, new OrderShippingDetailMapper());
        if (orderShippingDetails.isEmpty())
            throw new GowmaServiceRuntimeException(GowmaServiceExceptionCode.CFG_GENERIC_INVALID_ID, "Invalid Order Item ID");
        OrderShippingDetail orderShippingDetail = orderShippingDetails.get(0);
        return orderShippingDetail;
    }

    public OrderShippingDetail updateByOrderIdNId(Integer orderId, Integer orderShippingId, OrderShippingDetail orderShippingDetail) {
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(orderShippingDetail);
        namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, paramSource);
        return orderShippingDetail;
    }


    private class OrderShippingDetailMapper implements RowMapper<OrderShippingDetail> {

        @Override
        public OrderShippingDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderShippingDetail orderShippingDetail = new OrderShippingDetail();


            return orderShippingDetail;
        }
    }
}
