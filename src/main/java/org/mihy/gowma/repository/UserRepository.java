package org.mihy.gowma.repository;

import org.mihy.gowma.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private static String INSERT_SQL = "INSERT INTO gowma_user("+
            "gowma_user__email, gowma_user__mobile_no, gowma_user__password_hash, gowma_user__status, gowma_user__created_date)" +
            "VALUES (:gowma_user__email, :gowma_user__mobile_no, :gowma_user__password_hash, :gowma_user__status::status, :gowma_user__created_date)";

    private static String UPDATE_SQL ="UPDATE gowma_user" +
            "SET  gowma_user__email=:gowma_user__email, gowma_user__mobile_no=:gowma_user__mobile_no, gowma_user__password_hash=:gowma_user__password_hash, gowma_user__status=:gowma_user__status, gowma_user__last_modified_date=:gowma_user__last_modified_date, gowma_user__last_modified_by=:gowma_user__last_modified_by, gowma_user__is_deleted=:gowma_user__is_deleted" +
            "WHERE id=:id;";

    private static String SELECT_BY_ID_SQL ="SELECT * FROM gowma_user WHERE id=:id" ;

    private static String FIND_BY_EMAIL_SQL ="SELECT * FROM gowma_user WHERE gowma_user__email=:gowma_user__email" ;

    private static String SOFT_DELETE_BY_ID_SQL =" UPDATE gowma_user SET gowma_user__is_deleted=true";


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    public User create(final User user) {
        String passwordHash = passwordEncoder.encode(user.getPassword());
        MapSqlParameterSource paramNameToValuesMap = new MapSqlParameterSource();
        paramNameToValuesMap.registerSqlType("gowma_user__status",Types.VARCHAR);
        paramNameToValuesMap.addValue("gowma_user__email",user.getEmail());
        paramNameToValuesMap.addValue("gowma_user__mobile_no",user.getMobileNo());
        paramNameToValuesMap.addValue("gowma_user__password_hash",passwordHash);
        paramNameToValuesMap.addValue("gowma_user__created_date",LocalDateTime.now());
        paramNameToValuesMap.addValue("gowma_user__status",user.getStatus().name());



        namedParameterJdbcTemplate.update(INSERT_SQL,paramNameToValuesMap );
        return user;
    }

    public User update(User user) {
        String passwordHash = passwordEncoder.encode(user.getPassword());
        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("gowma_user__email",user.getEmail());
        paramNameToValuesMap.put("gowma_user__mobile_no",user.getMobileNo());
        paramNameToValuesMap.put("gowma_user__password_hash",passwordHash);
        paramNameToValuesMap.put("gowma_user__status",user.getStatus());
        paramNameToValuesMap.put("gowma_user__last_modified_date",LocalDateTime.now());
        paramNameToValuesMap.put("gowma_user__last_modified_by",user.getId());
        paramNameToValuesMap.put("id",user.getId());
        namedParameterJdbcTemplate.update(UPDATE_SQL,paramNameToValuesMap );
        return user;
    }

    public User get(int id) {
        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("id",id);
       List<User> users=namedParameterJdbcTemplate.query(SELECT_BY_ID_SQL, paramNameToValuesMap,new RowMapper<User>() {
           @Override
           public User mapRow(ResultSet resultSet, int i) throws SQLException {
               User user = new User();
               user.setId(resultSet.getInt("id"));
               user.setEmail(resultSet.getString("gowma_user__email"));
               user.setMobileNo(resultSet.getString("gowma_user__mobile_no"));
               user.setStatus(User.UserStatus.valueOf(resultSet.getString("gowma_user__status")));
               return user;
           }
       });
       if(users.isEmpty())
           return null;
        return users.get(0);
    }

    public void delete(int id) {
        namedParameterJdbcTemplate.getJdbcOperations().update(SOFT_DELETE_BY_ID_SQL);
    }

    public User findByEmail(String email) {

        Map<String, Object> paramNameToValuesMap = new HashMap<>();
        paramNameToValuesMap.put("gowma_user__email",email);
        List<User> users=namedParameterJdbcTemplate.query(FIND_BY_EMAIL_SQL, paramNameToValuesMap,new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("gowma_user__email"));
                user.setMobileNo(resultSet.getString("gowma_user__mobile_no"));
                user.setStatus(User.UserStatus.valueOf(resultSet.getString("gowma_user__status")));
                user.setPassword(resultSet.getString("gowma_user__password_hash"));
                return user;
            }
        });
        if(users.isEmpty())
            return null;
        return users.get(0);
    }
}
