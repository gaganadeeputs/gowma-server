/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.constants.AddressType;
import org.mihy.gowma.model.AddressCode;
import org.mihy.gowma.model.UserAddress;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class UserAddressRepository extends BaseRepository {

    private static final String INSERT_SQL = "INSERT INTO user_addresses(" +
            "user_address__user_id, user_address__address_code_id, user_address__address_type, user_address_name, user_address__address1, " +
            "user_address__address2, user_address__landmark, user_address__phone_no, user_address__is_default, user_address__created_date)" +
            "VALUES (:userId, :addressCode.id, :addressType::address_type, :addressName, :address1, :address2, :landmark, :phoneNo, :isDefault,:createdDate)";

    private static final String UPDATE_BY_USER_ID_SQL = "UPDATE user_addresses" +
            " SET  user_address__address_code=addressCode.id, user_address__address_type=addressType::address_type, user_address_name=:addressName," +
            " user_address__address1=:address1, user_address__address2=:address2, user_address__landmark=:landmark, user_address__phone_no=:phoneNo, user_address__is_default=:isDefault," +
            " user_address__last_modified_date=:lastModifiedDate" +
            " WHERE user_address__user_id=:userId";

    private static final String SELECT_BY_USER_ID_SQL = "SELECT * from user_addresses,address_code,state,country " +
            " WHERE user_addresses.user_address__address_code_id=address_code.id " +
            " AND  address_code.address_code__state_id=state.id " +
            " AND state.state__country_id=country.id " +
            " AND user_address__user_id=:userId";

    private static final String SOFT_DELETE_BY_USER_ID_SQL = "UPDATE  user_addresses set user_detail__is_deleted=true WHERE user_address__user_id=:userId";

    private static final String SOFT_DELETE_BY_ID_AND_USER_ID_SQL = "UPDATE  user_addresses set user_detail__is_deleted=true" +
            " WHERE id=:id AND user_address__user_id=:userId ";


    public List<UserAddress> create(List<UserAddress> userAddresses) {
        userAddresses.forEach(userAddress -> create(userAddress));
        return userAddresses;
    }

    public UserAddress create(UserAddress userAddress) {
        try {
            super.insert(userAddress, INSERT_SQL, new EnumBeanPropParamSource(userAddress));
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return userAddress;
    }

    public List<UserAddress> update(List<UserAddress> userAddresses) {
        userAddresses.forEach(userAddress -> update(userAddress));
        return userAddresses;
    }

    public UserAddress update(UserAddress userAddress) {
        try {
            namedParameterJdbcTemplate.update(UPDATE_BY_USER_ID_SQL, new EnumBeanPropParamSource(userAddress));
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return userAddress;
    }

    public void deleteForUserId(int userId) {

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_USER_ID_SQL, params);
    }

    public List<UserAddress> getByUserId(int userId) {

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        List<UserAddress> userAddresses = namedParameterJdbcTemplate.query(SELECT_BY_USER_ID_SQL, params, new UserAddressMapper());
        return userAddresses;
    }


    public void deleteByIdNUserId(Integer userId, Integer addressId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", addressId);
        params.addValue("userId", userId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID_AND_USER_ID_SQL, params);
    }


    public class UserAddressMapper implements RowMapper<UserAddress> {

        @Override
        public UserAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserAddress userAddress = new UserAddress();
            userAddress.setId(rs.getInt("id"));
            AddressCode addressCode = new AddressCode();
            addressCode.setCode(rs.getString("address_code__code"));
            addressCode.setPlaceName(rs.getString("address_code__place_name"));
            addressCode.setCountryName(rs.getString("country__name"));
            addressCode.setStateName(rs.getString("state__name"));
            addressCode.setId(rs.getInt("id"));
            userAddress.setAddressCode(addressCode);
            userAddress.setAddress1(rs.getString("user_address__address1"));
            userAddress.setAddress2(rs.getString("user_address__address2"));
            userAddress.setLandmark(rs.getString("user_address__landmark"));
            userAddress.setPhoneNo(rs.getString("user_address__phone_no"));
            userAddress.setAddressType(AddressType.valueOf(rs.getString("user_address__address_type")));
            userAddress.setDefault(rs.getBoolean("user_address__is_default"));
            return userAddress;
        }
    }
}
