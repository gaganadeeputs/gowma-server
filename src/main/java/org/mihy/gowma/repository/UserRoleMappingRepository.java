package org.mihy.gowma.repository;

import org.mihy.gowma.model.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by gdeepu on 19/11/17.
 */
@Repository
public class UserRoleMappingRepository extends BaseRepository {

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
}
