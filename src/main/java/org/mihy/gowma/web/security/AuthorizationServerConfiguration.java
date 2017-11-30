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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
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
    private PasswordEncoder bCryptPasswordEncoder;

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
				//android-secret
				.secret("$2a$10$3wxtJ2FgcWQqN5vYGJ/vruVzlttPYeV0e/A5HmzYFpj8ZH6xNXabK").refreshTokenValiditySeconds(refreshTokenValidityInSeconds);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
				.authenticationManager(authenticationManager)
				.tokenEnhancer(customTokenEnhancer)
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);;
	}

	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.passwordEncoder(bCryptPasswordEncoder);
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
	/*@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://gowma.cwynmbojexhg.us-west-2.rds.amazonaws.com:5432/gowma");
		dataSource.setUsername("gowma");
		dataSource.setPassword("MobileIron,2017");
		return dataSource;
	}
*/
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

/*	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public static class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {


	}*/


}
