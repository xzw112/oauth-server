package com.xzw.oauth.config;


import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(HttpSecurity httpSecurity)throws Exception {
        httpSecurity.authorizeRequests()
                //.antMatchers("/r/r1").hasAnyAuthority("p1")
                //.antMatchers("/login*").permitAll()
                .antMatchers("/client/*").permitAll()
                .antMatchers("/test/*").permitAll()
                .antMatchers("/link/*").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/serverAauth/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()// 关闭防跨域攻击功能,否则无法使用post请求
                .formLogin()
                .loginPage("/link/gotoLogin")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/link/gotoLogin?logout");
    }



}
