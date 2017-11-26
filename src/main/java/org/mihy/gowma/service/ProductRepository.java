/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.model.Product;
import org.mihy.gowma.model.search.ProductSearchRequest;
import org.mihy.gowma.repository.BaseRepository;
import org.mihy.gowma.repository.ProductImageRepository;
import org.mihy.gowma.repository.querybuilder.ProductQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository extends BaseRepository {


    private final String SELECT_BY_PARENT_CATEGORY_ID = "SELECT * FROM product WHERE product__category_id=:categoryId";

    private final String INSERT_SQL = "INSERT INTO product(product__category_id, product__unit_of_measure_id, " +
            "product__name, product__price, product__caption, product__description, product__is_active)" +
            "VALUES (:categoryId, :unitOfMeasure.id,:name, :caption, :price, :description, :isActive)";

    private final String UPDATE_BY_ID_SQL = "UPDATE product " +
            "SET product__category_id=:product__category_id, product__unit_of_measure_id=:unitOfMeasure.id, product__name=:name, " +
            "product__price=:price, product__caption=:caption, product__description=:description,product__is_active=:isActive " +
            " where id=:id";

    private final String SOFT_DELETE_BY_ID_SQL = "UPDATE product " +
            "SET product__is_deleted=true where id=:id";

    @Autowired
    private ProductQueryBuilder productQueryBuilder;

    @Autowired
    private ProductImageRepository productImageRepository;

    public List<Product> getProductCategoriesForParentId(Integer parentCategoryId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("product_category__parent_id", parentCategoryId);
        List<Product> products = namedParameterJdbcTemplate.query(SELECT_BY_PARENT_CATEGORY_ID, new productRowMapper());
        return products;
    }

    public Product update(Product product) {
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(product);
        namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, paramSource);
        return product;
    }

    public Product create(Product product) {
        super.insert(product, INSERT_SQL, new EnumBeanPropParamSource(product));
        return product;
    }

    public void delete(Integer productId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", productId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID_SQL, params);
        productImageRepository.deleteByProductId(productId);
    }

    public List<Product> findAll(ProductSearchRequest productSearchRequest) {
        List<Object> preparedStatementValues = new ArrayList<>();
        String searchQuery = productQueryBuilder.buildSearchQuery(productSearchRequest, preparedStatementValues);
        List<Product> products = namedParameterJdbcTemplate.getJdbcOperations().query(searchQuery, new productRowMapper());
        return products;
    }

    public List<Product> getById(Integer productId) {
        return null;
    }

    public class productRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setName(rs.getString("product_category__name"));
            product.setDescription(rs.getString("product_category__description"));
            return product;
        }
    }

}
