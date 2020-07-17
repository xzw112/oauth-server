package com.xzw.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class OauthServerApplication {
    public static void main(String[] args) {
        String hs = BCrypt.hashpw("admin", BCrypt.gensalt());
        System.out.println(hs);
        SpringApplication.run(OauthServerApplication.class, args);
    }
}
