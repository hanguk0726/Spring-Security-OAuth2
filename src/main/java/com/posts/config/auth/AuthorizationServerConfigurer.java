package com.posts.config.auth;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  private final DataSource dataSource;

  @Bean
  public TokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }

  @Bean
  public ApprovalStore approvalStore() {
    return new JdbcApprovalStore(dataSource);
  }

//  @Bean
//  public AuthorizationCodeServices jdbcAuthorizationCodeServices(DataSource dataSource) {
//    return new JdbcAuthorizationCodeServices(dataSource);
//  }

  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        .tokenStore(tokenStore())
//        .authorizationCodeServices(jdbcAuthorizationCodeServices(dataSource))
        .authenticationManager(authenticationManager)
        .approvalStore(approvalStore());
  }

  @Override
  public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    clients.jdbc(dataSource);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("permitAll()");

//        .checkTokenAccess("isAuthenticated()")
//        .allowFormAuthenticationForClients();
  }


}
