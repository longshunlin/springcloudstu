package com.cloud.oauth2.config;

import com.cloud.oauth2.entity.BaseUserDetail;
import com.cloud.oauth2.service.BaseUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description oauth2
 * @Author longshunlin
 * @Date 2019/1/29
 */
@Configuration
public class OauthConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BaseUserDetailService baseUserDetailService;

    @Autowired
    private DataSource dataSource;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用JdbcClientDetailsService客户端详情服务
//        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
        /*clients.jdbc(dataSource)
                .passwordEncoder(passwordEncoder)
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("authorization_code","refresh_token","password")
//                .scopes("read", "write")
                .scopes("all")
                .accessTokenValiditySeconds(3600) // 1 hour
                .refreshTokenValiditySeconds(2592000) // 30 days
                .and()
                .withClient("test1")
                .secret("test1Secret")
                .authorizedGrantTypes("authorization_code","refresh_token","password")
//                .scopes("server")
                .scopes("all")
                .and()
                .withClient("test2")
                .secret("test2Secret")
                .authorizedGrantTypes("authorization_code","refresh_token","password")
//                .scopes("server")
                .scopes("all")*/
        clients.inMemory().withClient("client").secret("{noop}secret")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token").scopes("all");
        ;
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                // 配置JwtAccessToken转换器
                .accessTokenConverter(accessTokenConverter())
                // refresh_token需要userDetailsService
//                .reuseRefreshTokens(false).userDetailsService(baseUserDetailService);
        .tokenStore(tokenStore());
    }

    /**
     * 使用非对称加密算法来对Token进行签名
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        final JwtAccessTokenConverter converter = new JwtAccessToken();
        // 导入证书
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));

        return converter;
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                // 开启/oauth/token_key验证端口无权限访问
                .tokenKeyAccess("permitAll()")
                // 开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()");
    }

/*******************************************************************************************************************************/
    /**
     * Jwt资源令牌转换器
     * @return accessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        return new JwtAccessTokenConverter(){

            /**
             * 重写增强token的方法
             * @param accessToken 资源令牌
             * @param authentication 认证
             * @return 增强的OAuth2AccessToken对象
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

                String userName = authentication.getUserAuthentication().getName();
                BaseUserDetail user = (BaseUserDetail) authentication.getUserAuthentication().getPrincipal();
                Map<String,Object> infoMap = new HashMap<>();
                infoMap.put("userName",userName);
                infoMap.put("roles",user.getAuthorities());
                ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(infoMap);
                return super.enhance(accessToken, authentication);
            }
        };
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
}
