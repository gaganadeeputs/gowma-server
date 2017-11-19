package org.mihy.gowma.web.security;

import org.mihy.gowma.web.security.oauth2.custom.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Value("${access.token.validity.in.minutes}")
	private int accessTokenValidityInMinutes;

	@Value("${refresh.token.validity.in.minutes}")
	private int refreshTokenValidityInMinutes;


	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenEnhancer customTokenEnhancer;

	@Autowired
	private TokenStore tokenStore;



	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		final int accessTokenValidityInSeconds = accessTokenValidityInMinutes * 60;
		final int refreshTokenValidityInSeconds = refreshTokenValidityInMinutes * 60;
		clients.inMemory().withClient("android-client")
				.authorizedGrantTypes("client-credentials","authorization_code", "password","refresh_token")
				.authorities("ROLE_CLIENT", "ROLE_ANDROID_CLIENT")
				.scopes("read", "write", "trust")
				.resourceIds("oauth2-resource")
				.accessTokenValiditySeconds(accessTokenValidityInSeconds)
				.secret("android-secret").refreshTokenValiditySeconds(refreshTokenValidityInSeconds);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
				.authenticationManager(authenticationManager)
				.tokenEnhancer(customTokenEnhancer)
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);;
	}


	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource());
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/gowma");
		dataSource.setUsername("gowma");
		dataSource.setPassword("mihy,2017");
		return dataSource;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public static class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {


	}


}
