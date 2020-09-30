package com.posts.web;


import com.posts.config.auth.OAuth2Token;
import com.posts.config.auth.OAuth2TokenService;
import com.posts.config.auth.UserDetailsServiceImpl;
import com.posts.service.posts.PostsService;
import com.posts.web.dto.PostsResponseDto;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Controller
public class IndexController {

  private final PostsService postsService;
  private final OAuth2TokenService oAuth2TokenService;
  private final UserDetailsServiceImpl userDetailsService;

  @GetMapping("/signup")
  public String signUp() {
    return "sign-up";
  }

  @GetMapping("/main")
  public String index(Model model, Principal principal) {
    String userName = userDetailsService.getSignedUpUserName(principal);

    model.addAttribute("user_name", userName);
    System.out.printf("userName is %s", userName);
    model.addAttribute("posts", postsService.findAllDesc());
    return "index";
  }


  @GetMapping("/posts/save")
  public String postsSave(Model
      model, Principal principal) {
    model.addAttribute("user_name", userDetailsService.getSignedUpUserName(principal));

    return "posts-save";
  }


  @GetMapping("/posts/update/{id}")
  public String postsUpdate(@PathVariable Long id, Model
      model, Principal principal) {
    PostsResponseDto dto = postsService.findById(id);
    model.addAttribute("post", dto);
    model.addAttribute("user_name", userDetailsService.getSignedUpUserName(principal));

    return "posts-update";
  }


  @GetMapping("/")
  public String getAuthorizationCode(HttpServletRequest request,Principal principal) {
    return userDetailsService.getAuthorizationCode(request,principal);
  }

  @GetMapping(value = "/callback")
  public void getOauthAccessToken(@RequestParam("code") String code,
      HttpServletResponse response, Principal principal) throws IOException {
    userDetailsService.getOauthAccessToken(code,response,principal);
  }
}
