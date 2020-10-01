package com.posts.web;

import com.posts.domain.user.Role;
import com.posts.domain.user.User;
import com.posts.domain.user.UserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @PostMapping("/signup")
  public User signupUser(@RequestBody Map<String, String> userInfo) {
    return userRepository.save(User.builder()
        .name(userInfo.get("name"))
        .password(passwordEncoder.encode(userInfo.get("password")))
        .role(Role.MEMBER)
        .build());
  }

}
