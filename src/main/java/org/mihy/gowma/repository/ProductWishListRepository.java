/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.model.Product;
import org.mihy.gowma.model.UnitOfMeasure;
import org.mihy.gowma.model.UserWishedProduct;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class ProductWishListRepository extends BaseRepository {

    private final String INSERT_SQL = "INSERT INTO user_product_wishlist(user_product_wishlist__user_id," +
            "user_product_wishlist__product_id" +
            ") VALUES(:userId,:product.id)";


    private final String SOFT_DELETE_BY_USER_ID_AND_PRODUCT_ID = "UPDATE user_shopping_cart_items " +
            "SET user_shopping_cart_items__is_deleted=true" +
            "WHERE user_product_wishlist__product_id=:productId AND " +
            "user_product_wishlist__user_id=:userId";


    private final String SOFT_DELETE_BY_USER_ID = "UPDATE user_shopping_cart_items " +
            "SET user_shopping_cart_items__is_deleted=true" +
            "WHERE user_shopping_cart_items__user_id=:userId ";

    private final String SELECT_BY_USER_ID = "SELECT * from user_shopping_cart_items usci INNER JOIN product p ON " +
            "usci.user_product_wishlist__product_id=product.id INNER JOIN unit_of_measure uof ON p.product__unit_of_measure_id=uof.id " +
            "WHERE user_shopping_cart_items__user_id=:userId ";


    public List<UserWishedProduct> getWishedProductsForUserId(Integer userId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("userId", userId);
        List<UserWishedProduct> userWishedProducts = namedParameterJdbcTemplate.query(SELECT_BY_USER_ID, paramNameToValueMap, new UserProductWishListMapper());
        return userWishedProducts;
    }

    public UserWishedProduct create(UserWishedProduct wishedProduct) {

        try {
            super.insert(wishedProduct, INSERT_SQL, new EnumBeanPropParamSource(wishedProduct));
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return wishedProduct;
    }

    public void delete(Integer userId, Integer productId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("userId", userId);
        paramNameToValueMap.addValue("productId", productId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_USER_ID_AND_PRODUCT_ID, paramNameToValueMap);
    }

    public void clearUserWishList(Integer userId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("userId", userId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_USER_ID, paramNameToValueMap);
    }


    private class UserProductWishListMapper implements RowMapper {
        @Override
        public UserWishedProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserWishedProduct userWishedProduct = new UserWishedProduct();
            userWishedProduct.setId(rs.getInt("id"));
            userWishedProduct.setUserId(rs.getInt("user_product_wishlist__user_id"));
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
            userWishedProduct.setProduct(product);
            return userWishedProduct;
        }
    }
}
