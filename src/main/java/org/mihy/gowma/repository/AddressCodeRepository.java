/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.repository;

import org.mihy.gowma.model.AddressCode;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AddressCodeRepository extends BaseRepository {


    private String SELECT_BY_ADDRESS_CODE_SQL = "SELECT * FROM address_code ac INNER JOIN state s ON ac.address_code__state_id=s.id" +
            " INNER JOIN country c ON s.state__country_id=c.id  WHERE address_code__code=:addressCode";

    public AddressCode getAddressCodeForCode(String addressCode) {
        final MapSqlParameterSource paramNameToValueMap = new MapSqlParameterSource();
        paramNameToValueMap.addValue("addressCode", addressCode);
        List<AddressCode> addressCodes = namedParameterJdbcTemplate.query(SELECT_BY_ADDRESS_CODE_SQL, paramNameToValueMap, new AddressCodeMapper());
        if (addressCodes.isEmpty())
            return null;
        return addressCodes.get(0);

    }

    private class AddressCodeMapper implements RowMapper<AddressCode> {
        @Override
        public AddressCode mapRow(ResultSet rs, int rowNum) throws SQLException {
            AddressCode addressCode = new AddressCode();
            addressCode.setId(rs.getInt("id"));
            addressCode.setStateName(rs.getString("state__name"));
            addressCode.setCountryName(rs.getString("country__name"));
            addressCode.setPlaceName(rs.getString("address_code__place_name"));
            return addressCode;
        }
    }
}
