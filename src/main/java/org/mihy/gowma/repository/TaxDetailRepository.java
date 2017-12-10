/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.model.TaxDetail;
import org.mihy.gowma.model.TaxType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TaxDetailRepository extends BaseRepository {


    private final String INSERT_SQL = "INSERT INTO tax_details(product_tax_detail__product_id,product_tax_detail__category_id," +
            "product_tax_details__tax_type_id," +
            "product_tax_details__tax_percentage," +
            "product_tax_details__created_date," +
            "product_tax_details__created_by" +
            ") VALUES(:productId,:categoryId,:taxType.id,:percentage,:createdDate,createdBy)";


    private final String UPDATE_BY_ID_SQL = "UPDATE product_inventory " +
            " SET product_tax_detail__product_id=:productId," +
            " product_tax_detail__category_id=:categoryId ," +
            " product_tax_details__tax_type_id=:taxType.id," +
            " product_tax_details__tax_percentage=:percentage," +
            " product_tax_details__last_modified_date=:lastModifiedDate," +
            " product_tax_details__last_modified_by=:lastModifiedBy" +
            " WHERE id=:id";


    private final String SOFT_DELETE_BY_ID_SQL = "UPDATE tax_details " +
            " SET tax_details__is_deleted=true" +
            " id=:id";

    private final String SELECT_BY_PRODUCT_ID = "SELECT * FROM tax_details td" +
            " INNER JOIN tax_type tt ON td.product_tax_details__tax_type_id=tt.id" +
            " WHERE td.product_tax_detail__product_id=:productId " +
            " AND td.product_tax_details__is_deleted=false";

    private final String SELECT_BY_CATEGORY_ID = "SELECT * FROM tax_details " +
            " INNER JOIN tax_type tt ON td.product_tax_details__tax_type_id=tt.id" +
            " WHERE td.product_tax_detail__category_id=:categoryId " +
            " AND td.product_tax_details__is_deleted=false";

    public List<TaxDetail> getTaxDetailsForCategoryIdRProductId(Integer categoryId, Integer productId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        String sql;
        if (productId != null) {
            sql = SELECT_BY_PRODUCT_ID;
            paramNameToValueMap.addValue("productId", productId);
        } else {
            sql = SELECT_BY_CATEGORY_ID;
            paramNameToValueMap.addValue("categoryId", categoryId);
        }
        List<TaxDetail> taxDetails = namedParameterJdbcTemplate.query(sql, paramNameToValueMap, new TaxDetailsRowMapper());
        return taxDetails;
    }

    public TaxDetail update(TaxDetail taxDetail) {
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(taxDetail);
        namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, paramSource);
        return taxDetail;
    }

    public TaxDetail create(TaxDetail taxDetail) {
        super.insert(taxDetail, INSERT_SQL, new EnumBeanPropParamSource(taxDetail));
        return taxDetail;
    }

    public void delete(Integer taxDetailId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", taxDetailId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID_SQL, params);

    }

    private class TaxDetailsRowMapper implements RowMapper<TaxDetail> {

        @Override
        public TaxDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            TaxDetail taxDetail = new TaxDetail();
            taxDetail.setId(rs.getInt("id"));
            taxDetail.setProductId(rs.getInt("product_tax_detail__product_id"));
            taxDetail.setCategoryId(rs.getInt("product_tax_detail__category_id"));
            taxDetail.setPercentage(rs.getDouble("product_tax_details__tax_percentage"));
            TaxType taxType = new TaxType();
            taxType.setId(rs.getInt("product_tax_details__tax_type_id"));
            taxType.setName(rs.getString("tax_type__name"));
            taxType.setPercent(rs.getDouble("tax_type__percent"));
            taxDetail.setTaxType(taxType);
            return taxDetail;
        }
    }
}
