/* Copyright 2017 Mihy, Inc.
* All rights reserved.
 */
package org.mihy.gowma.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@PropertySource(value = { "classpath:config/application-config.properties" }, ignoreResourceNotFound = true)
@ToString
public class ApplicationProperties {


    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String databaseUserName;

    @Value("${spring.datasource.password}")
    private String databasPassword;
}
