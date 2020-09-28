//package com.posts.config.auth;
//
//import com.openshy4j.domain.client.Client;
//import com.openshy4j.domain.client.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@RequiredArgsConstructor
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//  private final userRepository userRepository;
//
//  @Override
//  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//    String name = authentication.getName();
//    String password = authentication.getCredentials().toString();
//    Client client = userRepository.findClientByUsername(name).orElseThrow(() -> new UsernameNotFoundException("user is not exists"));
//    if (!new BCryptPasswordEncoder().matches(password, client.getPassword()))
//      throw new BadCredentialsException("password is not valid");
//    return new UsernamePasswordAuthenticationToken(name, password, client.getAuthorities());
//  }
//
//  @Override
//  public boolean supports(Class<?> authentication) {
//    return authentication.equals(
//        UsernamePasswordAuthenticationToken.class);
//  }
//}
