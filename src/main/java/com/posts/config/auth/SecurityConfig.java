package com.posts.config.auth;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final DataSource dataSource;

  //  private final JwtTokenProvider jwtTokenProvider;
  @Autowired
  @Qualifier("userDetailsServiceImplBean")
  private UserDetailsService userDetailsService;


//  @Override
//  protected void configure(AuthenticationManagerBuilder auth)
//      throws Exception {
//    auth.inMemoryAuthentication().withUser("admin")
//        .password("{noop}pass").roles("ADMIN");
//  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests().anyRequest().authenticated().and()
        .formLogin().and()
        .httpBasic();
  }


  @Bean("authenticationManagerBean")
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//    // BCryptPasswordEncoder(4) is used for users.password column
//    JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> cfg = auth.jdbcAuthentication()
//        .passwordEncoder(passwordEncoder()).dataSource(dataSource);
//
//    cfg.getUserDetailsService().setEnableGroups(true);
//    cfg.getUserDetailsService().setEnableAuthorities(false);
//  }

}
