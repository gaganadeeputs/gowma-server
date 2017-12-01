/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.model.File;
import org.mihy.gowma.model.ProductImage;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductImageRepository extends BaseRepository {

    private static final String INSERT_SQL = "INSERT INTO product_images(" +
            " product_images__product_id, product_images__image_file_id, product_images_order_no)" +
            "VALUES (:productId, :imgFile.id, :orderNo)";


    private static final String UPDATE_SQL = "UPDATE  product_images SET product_images__product_id=:productId," +
            " product_images__image_file_id=:imgFile.id, product_images_order_no=:orderNo" +
            " WHERE id=:id";

    private static final String SOFT_DELETE_BY_PRODUCT_ID = "UPDATE  product_images SET product_images__is_deleted=true " +
            "WHERE product_images__product_id=:productId";

    private static final String SOFT_DELETE_BY_PRODUCT_ID_AND_ID = "UPDATE  product_images SET product_images__is_deleted=true" +
            " WHERE product_images__product_id=:productId AND id=:id ";


    private static final String SOFT_DELETE_BY_ID = "UPDATE product_images SET product__images_is_deleted=true where  id=:id";

    private final String SELECT_BY_PRODUCT_ID = "SELECT * from product_images pi INNER JOIN file f ON pi.product_images__image_file_id=f.id" +
            " WHERE product_images__product_id=:productId" +
            " ORDER BY product_images_order_no ASC";


    public List<ProductImage> getByProductId(int productId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("productId", productId);
        List<ProductImage> productImages = namedParameterJdbcTemplate.query(SELECT_BY_PRODUCT_ID, paramNameToValueMap, new ProductImageRowMapper());
        return productImages;

    }

    public ProductImage update(ProductImage productImage) {
        EnumBeanPropParamSource paramSource = new EnumBeanPropParamSource(productImage);
        namedParameterJdbcTemplate.update(UPDATE_SQL, paramSource);
        return productImage;
    }

    public List<ProductImage> create(List<ProductImage> productImages) {

        for (ProductImage productImage : productImages) {
            create(productImage);
        }
        return productImages;
    }

    private ProductImage create(ProductImage productImage) {
        super.insert(productImage, INSERT_SQL, new EnumBeanPropParamSource(productImage));
        return productImage;
    }


    public void deleteByProductId(Integer productId) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("productId", productId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_PRODUCT_ID, paramNameToValueMap);
    }

    public void deleteById(Integer id) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("id", id);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID, paramNameToValueMap);
    }

    public void deleteByProductIdNId(Integer productId, Integer id) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("id", id);
        paramNameToValueMap.addValue("productId", id);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_PRODUCT_ID_AND_ID, paramNameToValueMap);
    }

    public class ProductImageRowMapper implements RowMapper<ProductImage> {

        @Override
        public ProductImage mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductImage productImage = new ProductImage();
            productImage.setId(rs.getInt("id"));
            File file = new File();
            file.setId(rs.getInt("product_images__image_file_id"));
            file.setPath(rs.getString("file__path"));
            file.setName("file__name");
            file.setSize(rs.getLong("file__size"));
            productImage.setImgFile(file);
            productImage.setOrderNo(rs.getInt("product_images_order_no"));
            return productImage;
        }
    }
}
