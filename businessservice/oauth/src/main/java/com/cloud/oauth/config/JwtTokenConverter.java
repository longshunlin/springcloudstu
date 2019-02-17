package com.cloud.oauth.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/29
 */
public class JwtTokenConverter extends JwtAccessTokenConverter {

    /**
     *  自定义需要封装到jwt 的数据
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication().getName();
        Object authorities=authentication.getUserAuthentication().getAuthorities();
        Object principal=authentication.getUserAuthentication().getPrincipal();
//        UaaAccount uaaAccount=(UaaAccount)principal;
        final Map<String, Object> additionalInformation = new HashMap<>();
        additionalInformation.put("accountName", userName);
        additionalInformation.put("accountId", "admin");
        additionalInformation.put("authorities", authorities);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        OAuth2AccessToken token = super.enhance(accessToken, authentication);
        return token;
    }

}
