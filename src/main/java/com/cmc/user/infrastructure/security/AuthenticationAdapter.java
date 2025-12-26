package com.cmc.user.infrastructure.security;

import com.cmc.global.auth.CustomUserDetails;
import com.cmc.user.application.port.out.AuthenticationPort;
import com.cmc.user.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAdapter implements AuthenticationPort {

    @Override
    public void persistAuthentication(User user) {
        CustomUserDetails userDetails = new CustomUserDetails(
                user.getId(),
                user.getNickname().value(),
                user.getRole().name()
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
