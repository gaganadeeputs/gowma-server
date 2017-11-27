/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.model.Product;
import org.mihy.gowma.model.ProductImage;
import org.mihy.gowma.model.ShoppingCartItem;
import org.mihy.gowma.model.UnitOfMeasure;
import org.mihy.gowma.model.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ShoppingCartItemsRepository extends BaseRepository {

    private final String INSERT_SQL = "INSERT INTO user_shopping_cart_items(user_shopping_cart_items__user_id," +
            "user_shopping_cart_items__product_id,user_shopping_cart_items__quantity" +
            ") VALUES(:userId,:product.id,:quantity)";

    private final String UPDATE_BY_ID_AND_USER_ID_SQL = "UPDATE user_shopping_cart_items " +
            "SET user_shopping_cart_items__quantity=:quantity" +
            "WHERE user_shopping_cart_items__user_id=:userId AND id=:id ";

    private final String SOFT_DELETE_BY_ID_AND_USER_ID_SQL = "UPDATE user_shopping_cart_items " +
            "SET user_shopping_cart_items__is_deleted=true" +
            "WHERE user_shopping_cart_items__user_id=:userId AND id=:id";

    private final String SOFT_DELETE_BY_USER_ID_SQL = "UPDATE user_shopping_cart_items " +
            "SET user_shopping_cart_items__is_deleted=true" +
            "WHERE user_shopping_cart_items__user_id=:userId ";

    private final String SELECT_BY_USER_ID = "SELECT * from user_shopping_cart_items usci INNER JOIN product p ON " +
            "usci.user_shopping_cart_items__product_id=product.id INNER JOIN unit_of_measure uof ON p.product__unit_of_measure_id=uof.id " +
            "WHERE user_shopping_cart_items__user_id=:userId ";


    public ShoppingCartItem create(ShoppingCartItem shoppingCartItem) {
        try {
            super.insert(shoppingCartItem, INSERT_SQL, new EnumBeanPropParamSource(shoppingCartItem));
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return shoppingCartItem;
    }

    public List<ShoppingCartItem> getCartItemsForUserId(Integer userId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("userId", userId);
        List<ShoppingCartItem> shoppingCartItems = namedParameterJdbcTemplate.query(SELECT_BY_USER_ID, paramNameToValueMap, new ShoppingCartItemMapper());
        return shoppingCartItems;
    }

    public void delete(Integer userId, Integer shoppingCartItemId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("shoppingCartItemId", shoppingCartItemId);
        paramNameToValueMap.addValue("userId", userId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID_AND_USER_ID_SQL, paramNameToValueMap);
    }

    public ShoppingCartItem update( ShoppingCartItem shoppingCartItem) {
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(shoppingCartItem);
        namedParameterJdbcTemplate.update(UPDATE_BY_ID_AND_USER_ID_SQL, paramSource);
        return shoppingCartItem;
    }

    public void clearAllShoppingCartListItemsForUserId(Integer userId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("userId", userId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_USER_ID_SQL, paramNameToValueMap);
    }


    public class ShoppingCartItemMapper implements RowMapper<ShoppingCartItem>{

        @Override
        public ShoppingCartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setId(rs.getInt("id"));
            shoppingCartItem.setUserId(rs.getInt("user_shopping_cart_items__user_id"));
            shoppingCartItem.setQuantity(rs.getDouble("user_shopping_cart_items__quantity"));
            Product product = new Product();
            product.setId(rs.getInt("user_shopping_cart_items__product_id"));
            product.setName(rs.getString("product__name"));
            product.setDescription(rs.getString("product__description"));
            product.setCaption(rs.getString("product__caption"));
            product.setPrice(rs.getDouble("product__price"));
            product.setViewCount(rs.getInt("product__view_count"));
            product.setActive(rs.getBoolean("product__is_active"));
            UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
            unitOfMeasure.setId(rs.getInt("product__unit_of_measure_id"));
            unitOfMeasure.setName(rs.getString("unit_of_measure__name"));
            product.setUnitOfMeasure(unitOfMeasure);
            shoppingCartItem.setProduct(product);
            return shoppingCartItem;
        }
    }
}
