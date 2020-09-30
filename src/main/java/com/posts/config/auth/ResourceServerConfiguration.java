package com.posts.config.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
    http
        .requestMatchers()
        .antMatchers("/api/**")
        .and()
        .authorizeRequests()
        .anyRequest().authenticated();
  }

}

