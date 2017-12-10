package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.exception.GowmaServiceExceptionCode;
import org.mihy.gowma.exception.GowmaServiceRuntimeException;
import org.mihy.gowma.model.Order;
import org.mihy.gowma.model.OrderCreationWrapper;
import org.mihy.gowma.model.OrderShippingDetail;
import org.mihy.gowma.model.OrderTransaction;
import org.mihy.gowma.model.search.OrderSearchRequest;
import org.mihy.gowma.repository.querybuilder.OrderItemRepository;
import org.mihy.gowma.repository.querybuilder.OrderQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository extends BaseRepository {


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

    @Autowired
    private OrderTransactionRepository orderTransactionRepository;

    @Autowired
    private OrderShippingDetailRepository orderShippingDetailRepository;

    @Transactional
    public Order create(OrderCreationWrapper orderCreationWrapper) {

        super.insert(orderCreationWrapper.getOrder(), INSERT_SQL, new EnumBeanPropParamSource(orderCreationWrapper.getOrder()));
        OrderTransaction orderTransaction = orderCreationWrapper.getOrderTransaction();
        orderItemRepository.create(orderCreationWrapper.getOrderItems());
        orderTransactionRepository.create(orderTransaction);
        OrderShippingDetail orderShippingDetail = new OrderShippingDetail();
        orderShippingDetail.setOrderTxnId(orderTransaction.getId());
        orderShippingDetailRepository.create(orderShippingDetail);
        return orderCreationWrapper.getOrder();
    }

    public Order update(Order order) {
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(order);
        namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, paramSource);
        return order;
    }

    public void delete(Integer orderId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", orderId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID_SQL, params);
    }

    public List<Order> findAll(OrderSearchRequest orderSearchRequest) {
        List<Object> preparedStatementValues = new ArrayList<>();
        String searchQuery = orderQueryBuilder.buildSearchQuery(orderSearchRequest, preparedStatementValues);
        List<Order> orders = namedParameterJdbcTemplate.getJdbcOperations().query(
                searchQuery, preparedStatementValues.toArray(), new OrderRowMapper());
        return orders;
    }

    public Order getById(Integer orderId) {
        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("id", orderId);
        List<Order> orders = namedParameterJdbcTemplate.query(SELECT_BY_ID_SQL, paramNameToValuesMap, new OrderRowMapper());
        if (orders.isEmpty())
            throw new GowmaServiceRuntimeException(GowmaServiceExceptionCode.CFG_GENERIC_INVALID_ID, "Invalid order ID");
        Order order = orders.get(0);

        return order;
    }


    private class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();


            return order;
        }
    }
}
