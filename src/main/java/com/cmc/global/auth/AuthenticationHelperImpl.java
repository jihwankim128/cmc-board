// com.cmc.user.infrastructure.security.CurrentUserAdapter.java
package com.cmc.global.auth;

import static com.cmc.global.auth.AuthExceptionStatus.UN_AUTHENTICATED;

import com.cmc.global.common.exception.client.UnAuthorizedException;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelperImpl implements AuthorizationHelper {

    @Override
    public Long getCurrentUserId() {
        try {
            CustomUserDetails authentication = getAuthentication();
            return Objects.requireNonNull(authentication).getUserId();
        } catch (ClassCastException e) {
            throw new IllegalStateException("Principal 타입 오류: Long이 아닙니다.");
        }
    }

    @Override
    public boolean isAdmin() {
        String role = getAuthentication().getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new IllegalStateException("권한 정보가 없습니다."));

        return Objects.requireNonNull(role).equals("ROLE_ADMIN");
    }

    private CustomUserDetails getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || Objects.requireNonNull(authentication.getPrincipal()).equals("anonymousUser")) {
            throw new UnAuthorizedException(UN_AUTHENTICATED);
        }
        return (CustomUserDetails) authentication;
    }
}