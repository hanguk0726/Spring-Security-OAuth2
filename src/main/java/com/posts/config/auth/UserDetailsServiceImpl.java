package com.posts.config.auth;

import com.posts.domain.user.Role;
import com.posts.domain.user.User;
import com.posts.domain.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("userDetailsServiceImplBean")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User createUser(String name, String password) {
    User user = User.builder().name(name).password(passwordEncoder.encode(password))
        .role(Role.ADMIN).build();

    return userRepository.save(user);
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> byUsername = userRepository.findByName(username);
    User user = byUsername.orElseThrow(() -> new UsernameNotFoundException(username));
    return user;

  }


//  private Collection<? extends GrantedAuthority> authorities() {
//    return Arrays.asList(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
//  }

}
