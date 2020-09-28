package com.posts.web;

import com.posts.config.auth.UserDetailsServiceImpl;
import com.posts.domain.user.Role;
import com.posts.domain.user.User;
import com.posts.domain.user.UserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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
    System.out.printf("test= %s and %s", userInfo.get("role"), Role.valueOf("ADMIN"));
    return userRepository.save(User.builder()
        .name(userInfo.get("name"))
        .password(passwordEncoder.encode(userInfo.get("password")))
        .role(Role.valueOf(userInfo.get("role")))
        .build());
  }
}
