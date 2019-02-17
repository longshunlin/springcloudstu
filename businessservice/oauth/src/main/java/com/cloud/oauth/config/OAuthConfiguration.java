package com.cloud.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager auth;

    @Autowired
    private DataSource dataSource;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    public JdbcTokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        //自定义的token生产器
        JwtAccessTokenConverter converter = new JwtTokenConverter();
        //设置token key值  解密时使用这个key可以解密
        converter.setSigningKey("key");
        return converter;
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security)
            throws Exception {
        security.passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints
                .authenticationManager(auth)
                .tokenStore(tokenStore())
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {

        clients.jdbc(dataSource)
                .passwordEncoder(passwordEncoder)
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("read", "write")
                .scopes("all")
                .accessTokenValiditySeconds(3600) // 1 hour
                .refreshTokenValiditySeconds(2592000) // 30 days
                .and()
                .withClient("test1")
                .secret("test1Secret")
                .authorizedGrantTypes("client_credentials", "refresh_token")
//                .scopes("server")
                .scopes("all")
                .and()
                .withClient("test2")
                .secret("test2Secret")
                .authorizedGrantTypes("client_credentials", "refresh_token")
//                .scopes("server")
                .scopes("all")
        ;

    }

    @Configuration
    @Order(-20)
    protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication().dataSource(dataSource)
//                    .withUser("longshunlin").password(new BCryptPasswordEncoder().encode("123456")).roles("USER")
//                    .and()
//                    .withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("ADMIN")
            ;
        }
    }

}
