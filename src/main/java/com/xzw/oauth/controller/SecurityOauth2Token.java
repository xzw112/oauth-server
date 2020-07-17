package com.xzw.oauth.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/serverAauth")
public class SecurityOauth2Token {

    final static long PORT = 8080;
    //clientId
    final static String CLIENT_ID = "c1";
    //clientSecret
    final static String CLIENT_SECRET = "secret";
    //用户名
    final static String USERNAME = "xzw";
    //密码
    final static String PASSWORD = "123456";
    //获取accessToken得URI
    final static String TOKEN_REQUEST_URI = "http://localhost:"+PORT+"/oauth/token?grant_type=password&username=" + USERNAME + "&password=" + PASSWORD;


    @RequestMapping(value = "/getAccessToken", method = RequestMethod.POST)
    public OAuth2AccessToken getAccessToken(){
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.TEXT_PLAIN );
        headers.add("authorization", getBasicAuthHeader());
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<OAuth2AccessToken> resp = rest.postForEntity( TOKEN_REQUEST_URI, entity, OAuth2AccessToken.class);
        if( !resp.getStatusCode().equals( HttpStatus.OK )){
            throw new RuntimeException( resp.toString() );
        }
        OAuth2AccessToken t = resp.getBody();
        return t;

    }

    /**
     * 构建header
     * @return
     */
    private String getBasicAuthHeader(){
        String auth = CLIENT_ID + ":" + CLIENT_SECRET;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

}
