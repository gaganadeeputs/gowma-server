/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.constants.UserStatus;
import org.mihy.gowma.exception.GowmaServiceExceptionCode;
import org.mihy.gowma.exception.GowmaServiceRuntimeException;
import org.mihy.gowma.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository extends BaseRepository {


    public static final Integer USER_ROLE_ID = 100000;

    private static final String INSERT_SQL = "INSERT INTO gowma_user(" +
            "gowma_user__email, gowma_user__mobile_no, gowma_user__password_hash, gowma_user__status, gowma_user__created_date)" +
            "VALUES (:email, :mobileNo, :password, :status::status, :createdDate)";

    private static final String UPDATE_SQL = "UPDATE gowma_user" +
            " SET  gowma_user__email=:email, gowma_user__mobile_no=:mobileNo, gowma_user__password_hash=:password, gowma_user__status=:status::status" +
            ", gowma_user__last_modified_date=:lastModifiedDate, gowma_user__last_modified_by=:lastModifiedBy" +
            " WHERE id=:id";

    private static String SELECT_BY_ID_SQL = "SELECT * FROM gowma_user WHERE id=:id";

    private static String FIND_BY_EMAIL_SQL = "SELECT * FROM gowma_user WHERE gowma_user__email=:email";

    private static String FIND_BY_MOBILE_NO_SQL = "SELECT * FROM gowma_user WHERE gowma_user__mobile_no=:mobileNo";

    private static String SOFT_DELETE_BY_ID_SQL = " UPDATE gowma_user SET gowma_user__is_deleted=true WHERE id=:id";


    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    @Transactional
    public User create(final User user) {
        try {
            super.insert(user, INSERT_SQL, new EnumBeanPropParamSource(user));
            User.UserDetail userDetail = user.getUserDetail() == null ? new User.UserDetail() : user.getUserDetail();
            userDetail.setUserId(user.getId());
            userDetailRepository.create(userDetail);
            userRoleMappingRepository.createMappingForUser(user.getId(), USER_ROLE_ID);
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return user;
    }

    public User get(int userId) {
        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("id", userId);
        List<User> users = namedParameterJdbcTemplate.query(SELECT_BY_ID_SQL, paramNameToValuesMap, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("gowma_user__email"));
                user.setMobileNo(resultSet.getString("gowma_user__mobile_no"));
                user.setStatus(UserStatus.valueOf(resultSet.getString("gowma_user__status")));
                return user;
            }
        });
        if (users.isEmpty())
            throw new GowmaServiceRuntimeException(GowmaServiceExceptionCode.CFG_GENERIC_INVALID_ID,"user");
        User user = users.get(0);
        user.setUserDetail(userDetailRepository.getByUserId(userId));
        user.setRoles(userRoleMappingRepository.getByUserId(userId));
        return user;
    }

    @Transactional
    public User update(User user) {
        try {
            if (namedParameterJdbcTemplate.update(UPDATE_SQL, new EnumBeanPropParamSource(user)) == 0)
                throw new GowmaServiceRuntimeException(GowmaServiceExceptionCode.CFG_GENERIC_INVALID_ID);
            user.getUserDetail().setUserId(user.getId());
            user.setUserDetail(userDetailRepository.update(user.getUserDetail()));
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return user;

    }

    @Transactional
    public void delete(int userId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", userId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_ID_SQL, params);
        userDetailRepository.deleteForUserId(userId);
        userAddressRepository.deleteForUserId(userId);
        userRoleMappingRepository.deleteForUserId(userId);
    }

    public User findByEmail(String email) {

        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("gowma_user__email", email);
        List<User> users = namedParameterJdbcTemplate.query(FIND_BY_EMAIL_SQL, paramNameToValuesMap, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("gowma_user__email"));
                user.setMobileNo(resultSet.getString("gowma_user__mobile_no"));
                user.setStatus(UserStatus.valueOf(resultSet.getString("gowma_user__status")));
                user.setPassword(resultSet.getString("gowma_user__password_hash"));
                return user;
            }
        });
        if (users.isEmpty())
            return null;
        return users.get(0);
    }


    public User findByMobileNumber(String mobileNumber) {

        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("mobileNo", mobileNumber);
        List<User> users = namedParameterJdbcTemplate.query(FIND_BY_MOBILE_NO_SQL, paramNameToValuesMap, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("gowma_user__email"));
                user.setMobileNo(resultSet.getString("gowma_user__mobile_no"));
                user.setStatus(UserStatus.valueOf(resultSet.getString("gowma_user__status")));
                user.setPassword(resultSet.getString("gowma_user__password_hash"));
                return user;
            }
        });
        if (users.isEmpty())
            return null;
        return users.get(0);
    }
}
