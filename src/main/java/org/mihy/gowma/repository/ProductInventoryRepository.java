package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.model.ProductInventory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductInventoryRepository extends BaseRepository {


    private final String INSERT_SQL = "INSERT INTO product_inventory(inventory__product_id,inventory__available_count,inventory__sold_count," +
            "inventory__inventory_status" +
            ") VALUES(:productId,:availableCount,:soldCount,:status::inventory_status)";


    private final String UPDATE_SQL = "UPDATE product_inventory " +
            "SET inventory__available_count=:availableCount," +
            "inventory__sold_count=:soldCount ," +
            "inventory__inventory_status=:status::inventory_status" +
            "WHERE  inventory__product_id=:productId AND" +
            "id=:id";


    private final String SOFT_DELETE_BY_PRODUCT_ID_AND_ID = "UPDATE product_inventory " +
            "SET inventory__is_deleted=true" +
            "WHERE inventory__product_id=:userId AND" +
            "id=:id";

    private final String SELECT_BY_PRODUCT_ID = "SELECT * FROM product_inventory WHERE product_inventory=:productId ";


    public void deleteByProductIdNId(Integer productId, Integer productInventoryId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("id", productInventoryId);
        paramNameToValueMap.addValue("productId", productId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_PRODUCT_ID_AND_ID, paramNameToValueMap);
    }

    public ProductInventory getByProductId(Integer productId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("productId", productId);
        List<ProductInventory> productInventories = namedParameterJdbcTemplate.query(SELECT_BY_PRODUCT_ID, paramNameToValueMap, new ProductInventoryMapper());
        if (productInventories.isEmpty())
            return null;
        return productInventories.get(0);

    }

    public ProductInventory create(ProductInventory productInventory) {
        try {
            super.insert(productInventory, INSERT_SQL, new EnumBeanPropParamSource(productInventory));
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return productInventory;
    }

    public ProductInventory update(ProductInventory productInventory) {
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(productInventory);
        namedParameterJdbcTemplate.update(UPDATE_SQL, paramSource);
        return productInventory;
    }

    public class ProductInventoryMapper implements RowMapper<ProductInventory> {

        @Override
        public ProductInventory mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductInventory productInventory = new ProductInventory();
            productInventory.setId(rs.getInt("id"));
            productInventory.setProductId(rs.getInt("inventory__product_id"));
            productInventory.setAvailableCount(rs.getInt("inventory__available_count"));
            productInventory.setSoldCount(rs.getInt("inventory__sold_count"));
            productInventory.setStatus(ProductInventory.InventoryStatus.valueOf(rs.getString("inventory__inventory_status")));
            return productInventory;
        }
    }
}
