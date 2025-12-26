package com.cmc.global.auth;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long userId;
    private final String nickname;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long userId, String nickname, String role) {
        this.userId = userId;
        this.nickname = nickname;
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.nickname;
    }
}