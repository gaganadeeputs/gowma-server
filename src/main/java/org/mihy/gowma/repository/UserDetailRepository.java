package org.mihy.gowma.repository;

import org.mihy.gowma.config.EnumBeanPropParamSource;
import org.mihy.gowma.constants.Gender;
import org.mihy.gowma.constants.UserStatus;
import org.mihy.gowma.model.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gdeepu on 19/11/17.
 */
@Repository
public class UserDetailRepository extends BaseRepository {


    private static final String  INSERT_SQL = "INSERT INTO user_detail(" +
            "user_detail_user_id, user_detail_fname, user_detail_lname, user_detail_gender, user_detail__created_date)" +
            "VALUES (:userId, :fname, :lname, :gender::gender, :createdDate)";

    private static String UPDATE_BY_USER_ID_SQL = "UPDATE user_detail" +
            "SET user_detail_fname=:fname, user_detail_lname=:lname, user_detail_gender=:gender::gender, user_detail__last_modified_date=:lastModifiedDate" +
            "WHERE user_detail_user_id=:userId";

    private static final String SOFT_DELETE_BY_USER_ID_SQL = " UPDATE user_detail SET user_detail__is_deleted=true WHERE user_detail_user_id=:userId";

    private static final String SELECT_BY_USER_ID ="SELECT * from user_detail WHERE user_detail_user_id=:userId ";

    public User.UserDetail create(User.UserDetail userDetail) {
        try {
            super.insert(userDetail, INSERT_SQL, new EnumBeanPropParamSource(userDetail));
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return userDetail;

    }

    public User.UserDetail getByUserId(int  userId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId",userId);
        List<User.UserDetail> userDetails = namedParameterJdbcTemplate.query(SELECT_BY_USER_ID, params, new RowMapper<User.UserDetail>() {
            @Override
            public User.UserDetail mapRow(ResultSet resultSet, int i) throws SQLException {
                User.UserDetail userDetail = new User.UserDetail();
                userDetail.setId(resultSet.getInt("id"));
                userDetail.setFname(resultSet.getString("user_detail_fname"));
                userDetail.setFname(resultSet.getString("user_detail_lname"));
                userDetail.setGender(Gender.valueOf(resultSet.getString("user_detail_gender")));
                return userDetail;
            }
        });
        if (userDetails.isEmpty())
            return null;
        return userDetails.get(0);

    }

    public User.UserDetail update(User.UserDetail userDetail) {

        try {
            namedParameterJdbcTemplate.update(UPDATE_BY_USER_ID_SQL, new EnumBeanPropParamSource(userDetail));
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return userDetail;
    }

    public void deleteForUserId(int userId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId",userId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_USER_ID_SQL,params);
    }
}
