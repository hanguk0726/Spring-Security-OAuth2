package com.posts.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posts.domain.user.User;
import com.posts.domain.user.UserRepository;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service("userDetailsServiceImplBean")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RestTemplate restTemplate;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> byUsername = userRepository.findByName(username);
    User user = byUsername.orElseThrow(() -> new UsernameNotFoundException(username));
    return user;

  }


  public String getSignedUpUserName(Principal principal) {
    return Optional.ofNullable(principal)
        .map(Principal::getName)
        .orElse("guest");
  }

  public String getAuthorizationCode(HttpServletRequest request, Principal principal) {

    String userName = getSignedUpUserName(principal);
    String oAuthClientId;
    if (userName.equals("admin")) {
      oAuthClientId = "admin";
    } else {
      oAuthClientId = "member";
    }
    request.getSession().setAttribute("oAuthClientId", oAuthClientId);
    StringBuilder builder = new StringBuilder();

    builder.append("redirect:");
    builder.append("http://localhost:8080/oauth/authorize");
    builder.append("?response_type=code");
    builder.append("&client_id=");
    builder.append(oAuthClientId);
    builder.append("&redirect_uri=");
    builder.append("http://localhost:8080/callback");

    return builder.toString();
  }


  public void getOAuthAccessToken(String code,
      HttpServletResponse response, Principal principal) throws IOException {

    String userName = getSignedUpUserName(principal);
    String oAuthClientId;
    if (userName.equals("admin")) {
      oAuthClientId = "admin";
    } else {
      oAuthClientId = "member";
    }
    String password;
    if (oAuthClientId.equals("admin")) {
      password = "admin";
    } else {
      password = "member";
    }
    String credentials = oAuthClientId + ":" + password;
    String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
    System.out.printf("credentials is %s", credentials);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Authorization", "Basic " + encodedCredentials);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("code", code);
    params.add("grant_type", "authorization_code");
    params.add("redirect_uri", "http://localhost:8080/callback");
    System.out.printf("params is %s", params);
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    ResponseEntity<String> _response = restTemplate
        .postForEntity("http://localhost:8080/oauth/token", request, String.class);
    System.out.printf("response is %s", _response.getStatusCode());
    System.out.println();
    System.out.printf("response.getBody is %s", _response.getBody());
    System.out.println();
    OAuth2Token accessToken;
    if (_response.getStatusCode() == HttpStatus.OK) {
      accessToken = new ObjectMapper().readValue(_response.getBody(), OAuth2Token.class);
      System.out.printf("received AccessToken = %s", accessToken);
      System.out.println();
      System.out.printf("stringAccessToken = %s", accessToken.getAccess_token());

      Cookie cookieAccessToken = new Cookie("access_token", accessToken.getAccess_token());

      response.addCookie(cookieAccessToken);
      response.sendRedirect("http://localhost:8080/main");
    }
  }
}


