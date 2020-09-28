package com.posts.config.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.posts.config.auth.domain.OAuthClientDetails;
import com.posts.config.auth.domain.OAuthclientDetailsRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class OAuth2TokenService {

  private final RestTemplate restTemplate;


  public OAuth2Token getOauthAccessToken(Map<String,String> info) throws JsonProcessingException {
    String oAuthClientId = info.get("clientId");
    String password = "";
    if(oAuthClientId.equals("admin")){
      password = "admin";
    }else {
      password = "member";
    }
    String credentials = oAuthClientId+":"+password;
    String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
    System.out.printf("credentials is %s", credentials);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Authorization", "Basic " + encodedCredentials);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("code", info.get("code"));
    params.add("grant_type", "authorization_code");
    params.add("redirect_uri", "http://localhost:8080/callback");
    System.out.printf("params is %s", params);
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    ResponseEntity<String> response = restTemplate
        .postForEntity("http://localhost:8080/oauth/token", request, String.class);
    System.out.printf("response is %s",response.getStatusCode());
    System.out.println();
    System.out.printf("response.getBody is %s",response.getBody());
    System.out.println();
    OAuth2Token accessToken = new ObjectMapper().readValue(response.getBody(), OAuth2Token.class);
    System.out.printf("received AccessToken = %s",accessToken);
    if (response.getStatusCode() == HttpStatus.OK) {
      return new ObjectMapper().readValue(response.getBody(), OAuth2Token.class);
    }
    return null;
  }
}
