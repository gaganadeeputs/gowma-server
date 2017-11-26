/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.model.Role;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class UserRoleMappingRepository extends BaseRepository {


    private static final String INSERT_SQL = "INSERT INTO  user_role_mapping(user_role_mapping__user_id,user_role_mapping__role_id) " +
            "values(:userId,:userRoleId )";
    private static final String SOFT_DELETE_BY_USER_ID_SQL = "UPDATE user_role_mapping" +
            "SET user_role_mapping__is_deleted=true" +
            "WHERE user_role_mapping__user_id=:userId";

    private static final String SELECT_BY_USER_ID_SQL = "SELECT * from user_role_mapping,role " +
            "WHERE user_role_mapping.user_role_mapping__role_id=role.id " +
            "AND user_role_mapping__user_id=:userId";

    public void deleteForUserId(int userId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        namedParameterJdbcTemplate.update(SOFT_DELETE_BY_USER_ID_SQL, params);
    }

    public List<Role> getByUserId(int userId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        List<Role> roles = namedParameterJdbcTemplate.query(SELECT_BY_USER_ID_SQL, params, new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setName(resultSet.getString("role__name"));
                return role;
            }
        });
        return roles;
    }


    public void createMappingForUser(int userId, Integer userRoleId) {

        try {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userId", userId);
            params.addValue("userRoleId", userRoleId);
            namedParameterJdbcTemplate.update(INSERT_SQL, params);
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
    }
}
