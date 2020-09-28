package com.posts.domain.user;

import com.posts.domain.BaseTimeEntity;
import java.util.Arrays;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseTimeEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User (String name, String password, Role role) {

        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User update(String name, String password) {
        this.name = name;
        this.password = password;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.role.getValue()));
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
