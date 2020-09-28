package com.posts.config.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@Order(3)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId("resource_id").stateless(false);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
//    http
//        .authorizeRequests()
//        .anyRequest().permitAll();

//    http
//        .csrf().disable()
//        .requestMatchers()
//        .antMatchers("/api/**")
//        .and()
//        .authorizeRequests()
//        .antMatchers("/**").hasRole("ADMIN")
//        .and()
//        .antMatcher("/authorization_code")
//        .anonymous()
//        .and()
//        .httpBasic()
//        .and()
//        .formLogin();

//        .authorizeRequests()
//        .antMatchers("/api/v1/posts")
//        .anonymous()
//        .anyRequest().authenticated().and()
//        .requestMatchers().antMatchers("/test")
//        .and().formLogin();

//        .authorizeRequests().antMatchers("/oauth/**","/user/**","/oauth/authorize","/callback").permitAll()
//        .antMatchers("/api/**").access("#oauth2.hasScope('write_posts')")
//        .antMatchers("/posts/**").access("#oauth2.hasScope('read_posts')")
//        .and().formLogin();
    http
        .requestMatchers()
        .antMatchers("/api/**")
        .and()
        .authorizeRequests()
        .antMatchers("/**").authenticated()
        .and()
        .httpBasic();
  }

//    http.headers().frameOptions().disable();
//    http.authorizeRequests()
//        .antMatchers("/v2/images").access("#oauth2.hasScope('read_profile')")
//        .anyRequest().authenticated();
}

