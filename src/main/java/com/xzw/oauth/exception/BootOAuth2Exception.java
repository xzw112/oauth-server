package com.xzw.oauth.exception;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author xzw
 * @date 2020年7月16日09:29:55
 * @description: 接口.
 */
@JsonSerialize(using = BootOAuthExceptionJacksonSerializer.class)
public class BootOAuth2Exception extends OAuth2Exception {

    public BootOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public BootOAuth2Exception(String msg) {
        super(msg);
    }
}
