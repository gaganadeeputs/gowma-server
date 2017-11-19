package org.mihy.gowma.config;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

/**
 * Created by gdeepu on 19/11/17.
 */
public class EnumBeanPropParamSource extends BeanPropertySqlParameterSource {

    public EnumBeanPropParamSource(final Object domain) {
        super(domain);
    }

    @Override
    public Object getValue(final String paramName)
            throws IllegalArgumentException {
        final Object value = super.getValue(paramName);
        if (value instanceof Enum) {
            return value.toString();
        }
        return value;
    }
}
