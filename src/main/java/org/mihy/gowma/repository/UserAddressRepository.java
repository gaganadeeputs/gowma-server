package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.constants.AddressType;
import org.mihy.gowma.constants.Gender;
import org.mihy.gowma.model.AddressCode;
import org.mihy.gowma.model.User;
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
            "SET  user_address__address_code=addressCode.id, user_address__address_type=addressType::address_type, user_address_name=:addressName," +
            " user_address__address1=:address1, user_address__address2=:address2, user_address__landmark=:landmark, user_address__phone_no=:phoneNo, user_address__is_default=:isDefault," +
            " user_address__last_modified_date=:lastModifiedDate" +
            "WHERE user_address__user_id=:userId";

    private static final String SELECT_BY_USER_ID_SQL = "SELECT * from user_addresses,address_code,state,country " +
            "where user_addresses.user_address__address_code_id=address_code.id " +
            "AND  address_code.address_code__state_id=state.id " +
            "AND state.state__country_id=country.id " +
            "AND user_address__user_id=:userId";

    private static final String SOFT_DELETE_BY_USER_ID_SQL = "UPADTE  user_addresses set user_detail__is_deleted=true where user_address__user_id=:userId";



    public List<User.UserAddress> create(List<User.UserAddress> userAddresses) {
            userAddresses.forEach(userAddress -> create(userAddress));
            return userAddresses;
    }

    public User.UserAddress create(User.UserAddress userAddress) {
        try {
            super.insert(userAddress, INSERT_SQL, new EnumBeanPropParamSource(userAddress));
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return userAddress;
    }

    public List<User.UserAddress> update(List<User.UserAddress> userAddresses) {
        userAddresses.forEach(userAddress -> update(userAddress));
        return userAddresses;
    }

    public User.UserAddress update(User.UserAddress userAddress) {
        try {
            namedParameterJdbcTemplate.update(UPDATE_BY_USER_ID_SQL, new EnumBeanPropParamSource(userAddress));
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return userAddress;
    }

    public void deleteForUserId(int userId) {

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId",userId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_USER_ID_SQL,params);
    }

    public List<User.UserAddress> getByUserId(int userId) {

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId",userId);
        List<User.UserAddress> userAddresses = namedParameterJdbcTemplate.query(SELECT_BY_USER_ID_SQL, params, new RowMapper<User.UserAddress>() {
            @Override
            public User.UserAddress mapRow(ResultSet resultSet, int i) throws SQLException {
                User.UserAddress userAddress = new User.UserAddress();
                userAddress.setId(resultSet.getInt("id"));
                AddressCode addressCode = new AddressCode();
                addressCode.setCode(resultSet.getString("address_code__code"));
                addressCode.setPlaceName(resultSet.getString("address_code__place_name"));
                addressCode.setCountryName(resultSet.getString("country__name"));
                addressCode.setStateName(resultSet.getString("state__name"));
                addressCode.setId(resultSet.getInt("id"));
                userAddress.setAddressCode(addressCode);
                userAddress.setAddress1(resultSet.getString("user_address__address1"));
                userAddress.setAddress2(resultSet.getString("user_address__address2"));
                userAddress.setLandmark(resultSet.getString("user_address__landmark"));
                userAddress.setPhoneNo(resultSet.getString("user_address__phone_no"));
                userAddress.setAddressType(AddressType.valueOf(resultSet.getString("user_address__address_type")));
                userAddress.setDefault(resultSet.getBoolean("user_address__is_default"));
                return userAddress;
            }
        });
        return userAddresses;
    }
}
