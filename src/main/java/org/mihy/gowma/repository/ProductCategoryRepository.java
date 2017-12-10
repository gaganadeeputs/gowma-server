/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.exception.GowmaServiceExceptionCode;
import org.mihy.gowma.exception.GowmaServiceRuntimeException;
import org.mihy.gowma.model.File;
import org.mihy.gowma.model.ProductCategory;
import org.mihy.gowma.model.search.ProductCategorySearchRequest;
import org.mihy.gowma.repository.querybuilder.ProductCategoryQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductCategoryRepository extends BaseRepository {


    private final String SELECT_BY_PARENT_CATEGORY_ID = "SELECT * FROM product_category pc" +
            " LEFT OUTER JOIN file ON pc.product_category__image_file_id=file.id" +
            " WHERE pc.product_category__parent_id=:parentCategoryId" +
            " AND pc.product_category__is_deleted=false";

    private final String SELECT_WHERE_PARENT_CATEGORY_ID_IS_NULL = "SELECT * FROM product_category pc " +
            " LEFT OUTER JOIN file ON pc.product_category__image_file_id=file.id " +
            " WHERE pc.product_category__parent_id is null" +
            " AND pc.product_category__is_deleted=false";

    private final String INSERT_SQL_WITH_IMAGE = "INSERT INTO product_category( " +
            "product_category__parent_id, product_category__name, product_category__description, " +
            "product_category__image_file_id, product_category__order_no, product_category__enabled ," +
            "product_category__created_date,product_category__created_by) " +
            "VALUES (:parentCategoryId, :name, :description, :imgFile.id, :orderNo, :enabled ,:createdDate,:createdBy)";

    private final String INSERT_SQL_WITH_OUT_IMAGE = "INSERT INTO product_category( " +
            "product_category__parent_id, product_category__name, product_category__description, " +
            " product_category__order_no, product_category__enabled ," +
            "product_category__created_date,product_category__created_by) " +
            "VALUES (:parentCategoryId, :name, :description, :orderNo, :enabled ,:createdDate,:createdBy)";


    private final String UPDATE_BY_ID_WITH_IMAGE_SQL = "UPDATE product_category " +
            " SET product_category__parent_id=:parentCategoryId, " +
            " product_category__name=:name," +
            " product_category__description=:description, " +
            " product_category__image_file_id=:imgFile.id," +
            " product_category__order_no=:orderNo," +
            " product_category__enabled=:enabled, " +
            " product_category__last_modified_date=:lastModifiedDate " +
            " product_category__last_modified_by=:product_category__last_modified_by" +
            " WHERE id=:id";

    private final String UPDATE_BY_ID_WITH_OUT_IMAGE_SQL = "UPDATE product_category " +
            " SET product_category__parent_id=:parentCategoryId, " +
            " product_category__name=:name," +
            " product_category__description=:description," +
            " product_category__order_no=:orderNo," +
            " product_category__enabled=:enabled, " +
            " product_category__last_modified_date=:lastModifiedDate " +
            " product_category__last_modified_by=:product_category__last_modified_by" +
            " WHERE id=:id";

    private final String SOFT_DELETE_BY_ID_SQL = "UPDATE product_category " +
            " SET product_category__is_deleted=true" +
            " WHERE id=:id";

    @Autowired
    private ProductCategoryQueryBuilder productCategoryQueryBuilder;

    public List<ProductCategory> getProductCategoriesForParentId(Optional<Integer> parentCategoryId) {
        List<ProductCategory> productCategories;
        if (parentCategoryId.isPresent()) {

            final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
            paramNameToValueMap.addValue("parentCategoryId", parentCategoryId.get());
            productCategories = namedParameterJdbcTemplate.query(SELECT_BY_PARENT_CATEGORY_ID, paramNameToValueMap, new ProductCategoryRowMapper());
        } else {
            productCategories = namedParameterJdbcTemplate.query(SELECT_WHERE_PARENT_CATEGORY_ID_IS_NULL, new ProductCategoryRowMapper());
        }
        if (productCategories.isEmpty())
            throw new GowmaServiceRuntimeException(GowmaServiceExceptionCode.CFG_GENERIC_INVALID_ID);
        return productCategories;
    }

    public ProductCategory update(ProductCategory productCategory) {
        String updateSql = UPDATE_BY_ID_WITH_OUT_IMAGE_SQL;
        if (productCategory.getImgFile() != null) {
            updateSql = UPDATE_BY_ID_WITH_IMAGE_SQL;
        }
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(productCategory);
        namedParameterJdbcTemplate.update(updateSql, paramSource);
        return productCategory;
    }

    public ProductCategory create(ProductCategory productCategory) {
        String insertSql = INSERT_SQL_WITH_OUT_IMAGE;
        if (productCategory.getImgFile() != null) {
            insertSql = INSERT_SQL_WITH_IMAGE;
        }
        super.insert(productCategory, insertSql, new EnumBeanPropParamSource(productCategory));
        return productCategory;
    }

    public void delete(Integer productCategory) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", productCategory);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID_SQL, params);
    }

    public List<ProductCategory> findAll(ProductCategorySearchRequest productCategorySearchRequest) {
        List<Object> preparedStatementValues = new ArrayList<>();
        String searchQuery = productCategoryQueryBuilder.buildSearchQuery(productCategorySearchRequest, preparedStatementValues);
        List<ProductCategory> productCategories = namedParameterJdbcTemplate.getJdbcOperations().query(searchQuery, preparedStatementValues.toArray(), new ProductCategoryRowMapper());
        return productCategories;
    }

    public class ProductCategoryRowMapper implements RowMapper<ProductCategory> {

        @Override
        public ProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setId(rs.getInt("id"));
            productCategory.setName(rs.getString("product_category__name"));
            productCategory.setDescription(rs.getString("product_category__description"));
            productCategory.setEnabled(rs.getBoolean("product_category__enabled"));
            if (rs.getInt("product_category__image_file_id") != 0) {
                File file = new File();
                file.setId(rs.getInt("product_category__image_file_id"));
                file.setPath(rs.getString("file__path"));
                file.setName(rs.getString("file__name"));
                file.setSize(rs.getLong("file__size"));
                productCategory.setImgFile(file);
            }
            productCategory.setOrderNo(rs.getInt("product_category__order_no"));
            productCategory.setParentCategoryId(rs.getInt("product_category__parent_id"));
            return productCategory;
        }
    }

}
