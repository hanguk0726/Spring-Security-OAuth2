package com.posts.config.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Entity
@Table(name="oauth_client_details")
public class OAuthClientDetails {

  @Id
  @Column( nullable = false, length = 256)
  String clientId;

  @Column(length = 256)
  String resourceIds;
  @Column(nullable = false, length = 256)
  String clientSecret;
  @Column(length = 256)
  String scope;
  @Column(nullable = false, length = 256)
  String authorizedGrantTypes;
  @Column(nullable = false, length = 256)
  String webServerRedirectUri;
  @Column(length = 256)
  String authorities;
  @Column(nullable = false)
  int accessTokenValidity;
  @Column(nullable = false)
  int refreshTokenValidity;
  @Column(length = 4096)
  String additionalInformation;
  @Column(nullable = false)
  String autoapprove;



}
