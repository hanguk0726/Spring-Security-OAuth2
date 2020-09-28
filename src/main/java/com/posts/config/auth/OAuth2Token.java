package com.posts.config.auth;

import lombok.Data;

@Data
public class OAuth2Token {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}