package com.posts.web;


import com.posts.config.auth.OAuth2Token;
import com.posts.config.auth.OAuth2TokenService;
import com.posts.service.posts.PostsService;
import com.posts.web.dto.PostsResponseDto;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@Controller
public class IndexController {

  private final PostsService postsService;
  private final OAuth2TokenService oAuth2TokenService;


  @GetMapping("/main")
  public String index(Model model) {

    model.addAttribute("posts", postsService.findAllDesc());
    return "index";
  }


  @GetMapping("/posts/save")
  public String postsSave() {
    return "posts-save";
  }


  @GetMapping("/posts/update/{id}")
  public String postsUpdate(@PathVariable Long id, Model
      model) {
    PostsResponseDto dto = postsService.findById(id);
    model.addAttribute("post", dto);

    return "posts-update";
  }


  @GetMapping("/")
  public String getAuthorizationCode(HttpServletRequest request) {

    String oAuthClientId = SecurityContextHolder.getContext().getAuthentication().getName();
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


  @GetMapping(value = "/callback")
  public void getOauthAccessToken(@RequestParam("code") String code,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException, URISyntaxException {
    Map<String, String> info = new HashMap<>();
    String oAuthClientId = (String) request.getSession().getAttribute("oAuthClientId");
    System.out.printf("getting attribute = %s", oAuthClientId);
    info.put("clientId", oAuthClientId);
    info.put("code", code);
    OAuth2Token accessToken = oAuth2TokenService.getOauthAccessToken(info);
    System.out.println();
    System.out.printf("stringAccessToken = %s", accessToken.getAccess_token());
    URI redirectUri = new URI("http://localhost:8080");

    Cookie cookie = new Cookie("access_token", accessToken.getAccess_token());
    response.addCookie(cookie);
    response.sendRedirect("http://localhost:8080/main");
  }
}
