package com.posts.config.auth;

import com.posts.domain.user.Role;
import com.posts.domain.user.User;
import com.posts.domain.user.UserRepository;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Service("userDetailsServiceImplBean")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final OAuth2TokenService oAuth2TokenService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> byUsername = userRepository.findByName(username);
    User user = byUsername.orElseThrow(() -> new UsernameNotFoundException(username));
    return user;

  }

  public User createUser(String name, String password) {
    User user = User.builder().name(name).password(passwordEncoder.encode(password))
        .role(Role.ADMIN).build();

    return userRepository.save(user);
  }

  public String getSignedUpUserName(Principal principal){
    return Optional.ofNullable(principal)
        .map(Principal::getName)
        .orElse("guest");
  }

  public String getAuthorizationCode(HttpServletRequest request,Principal principal) {

    String userName = getSignedUpUserName(principal);
    String oAuthClientId;
    if(userName.equals("admin")){
      oAuthClientId="admin";
    }else {
      oAuthClientId="member";
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


  public void getOauthAccessToken( String code,
      HttpServletResponse response, Principal principal) throws IOException {

    Map<String, String> info = new HashMap<>();
    String userName = getSignedUpUserName(principal);
    String oAuthClientId;
    if(userName.equals("admin")){
      oAuthClientId="admin";
    }else {
      oAuthClientId="member";
    }
    System.out.printf("getting attribute = %s", oAuthClientId);
    info.put("clientId", oAuthClientId);
    info.put("code", code);
    OAuth2Token accessToken = oAuth2TokenService.getOauthAccessToken(info);
    System.out.println();
    System.out.printf("stringAccessToken = %s", accessToken.getAccess_token());

    Cookie cookieAccessToken = new Cookie("access_token", accessToken.getAccess_token());

    response.addCookie(cookieAccessToken);
    response.sendRedirect("http://localhost:8080/main");
  }


}
