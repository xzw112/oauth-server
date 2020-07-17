package com.xzw.oauth.config;

import com.xzw.oauth.exception.BootOAuth2WebResponseExceptionTranslator;
import com.xzw.oauth.service.SpringDataUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 配置客户端详情信息服务
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager; // 认证管理器
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices; // 授权码
    @Autowired
    private SpringDataUserDetailService userDetailsService; // 用户详情
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    // 密码编辑器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // 认证管理器
    @Bean
    public AuthenticationManager authenticationManager() throws  Exception{
        AuthenticationManager authenticationManager = new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return daoAuthenticationProvider().authenticate(authentication);
            }
        };
        return authenticationManager;
    }




    // 令牌管理服务
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService); // 客户端信息服务
        services.setSupportRefreshToken(true); // 是否产生刷新令牌
        services.setTokenStore(tokenStore); // 令牌存储策略
        // 令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        services.setTokenEnhancer(tokenEnhancerChain);

        services.setAccessTokenValiditySeconds(7200); // 令牌默认有效期--2小时
        services.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期--3天
        return services;
    }

    // 设置授权码模式的授权码如何存取
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    // 将客户端的信息存储到数据库
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService)clientDetailsService).setPasswordEncoder(passwordEncoder());
        return clientDetailsService;
    }

    // 配置客户端详细信息服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory() // TODO 注意 修改成数据库存储
//                .withClient("c1")// 客户端id
//                .secret(new BCryptPasswordEncoder().encode("secret"))//客户端秘钥
//                .resourceIds("res1")// 资源列表
//                .authorizedGrantTypes("authorization_code", "password", "refresh_token")// 允许client访问的类型
//                .scopes("all") // 允许的授权范围
//                .autoApprove(false) // false :如果使用的是授权码模式，会跳转到授权页面
//                .redirectUris("http://www.baidu.com");
        clients.withClientDetails(clientDetailsService);
    }

    // 令牌访问端点
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) // 密码模式
                .authorizationCodeServices(authorizationCodeServices) // 授权码模式
                .tokenServices(tokenServices()) // 令牌管理服务
                .exceptionTranslator(new BootOAuth2WebResponseExceptionTranslator()) // 重写oauth2默认异常
                .allowedTokenEndpointRequestMethods(HttpMethod.POST); // 允许post提交
    }


    // 令牌访问端点的安全策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");  // 设置token_key可以公开访问
        security.checkTokenAccess("permitAll()"); // check_token公开
        security.allowFormAuthenticationForClients();// 表单认证 (申请令牌)，
    }





}
