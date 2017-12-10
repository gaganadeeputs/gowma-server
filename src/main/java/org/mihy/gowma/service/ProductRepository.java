/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.exception.GowmaServiceExceptionCode;
import org.mihy.gowma.exception.GowmaServiceRuntimeException;
import org.mihy.gowma.model.Product;
import org.mihy.gowma.model.UnitOfMeasure;
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


    private final String SELECT_BY_PARENT_CATEGORY_ID = "SELECT * FROM product p" +
            " INNER JOIN unit_of_measure uof ON uof.id=p.product__unit_of_measure_id " +
            " WHERE p.product__category_id=:categoryId" +
            " AND p.product__is_deleted=false";

    private final String INSERT_SQL = "INSERT INTO product(product__category_id, product__unit_of_measure_id, " +
            "product__name, product__price, product__caption, product__description, product__is_active ," +
            "product__created_date,product__created_by)" +
            "VALUES (:categoryId, :unitOfMeasure.id,:name, :price,:caption, :details, :active , :createdDate ,:createdBy)";

    private final String UPDATE_BY_ID_SQL = "UPDATE product " +
            " SET product__category_id=:categoryId, product__unit_of_measure_id=:unitOfMeasure.id, product__name=:name, " +
            " product__price=:price, product__caption=:caption, product__description=:details,product__is_active=:active " +
            " product__last_modified_date=:lastModifiedDate,product__last_modified_by=:lastModifiedBy" +
            " WHERE id=:id";

    private final String SOFT_DELETE_BY_ID_SQL = "UPDATE product " +
            " SET product__is_deleted=true where id=:id";


    private final String SELECT_BY_ID = "SELECT * FROM product p INNER JOIN unit_of_measure uof ON uof.id=product__unit_of_measure_id " +
            " WHERE p.id=:id" +
            " AND p.product__is_deleted=false";

    @Autowired
    private ProductQueryBuilder productQueryBuilder;

    @Autowired
    private ProductImageRepository productImageRepository;

    public List<Product> getProductCategoriesForParentId(Integer categoryId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("categoryId", categoryId);
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
        List<Product> products = namedParameterJdbcTemplate.getJdbcOperations().query(searchQuery, preparedStatementValues.toArray(), new productRowMapper());
        return products;
    }

    public Product getById(Integer productId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("id", productId);
        List<Product> products = namedParameterJdbcTemplate.query(SELECT_BY_ID, paramNameToValueMap, new productRowMapper());
        if (products.isEmpty())
            throw new GowmaServiceRuntimeException(GowmaServiceExceptionCode.CFG_GENERIC_INVALID_ID,productId);
        return products.get(0);
    }

    public class productRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("product__name"));
            product.setDetails(rs.getString("product__description"));
            product.setCaption(rs.getString("product__caption"));
            product.setPrice(rs.getDouble("product__price"));
            product.setViewCount(rs.getInt("product__view_count"));
            product.setActive(rs.getBoolean("product__is_active"));
            UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
            unitOfMeasure.setId(rs.getInt("product__unit_of_measure_id"));
            unitOfMeasure.setName(rs.getString("unit_of_measure__name"));
            product.setUnitOfMeasure(unitOfMeasure);
            return product;
        }
    }

}
