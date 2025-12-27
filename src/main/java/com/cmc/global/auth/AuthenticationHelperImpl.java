// com.cmc.user.infrastructure.security.CurrentUserAdapter.java
package com.cmc.global.auth;

import static com.cmc.global.auth.AuthExceptionStatus.UN_AUTHENTICATED;

import com.cmc.global.common.exception.client.UnAuthorizedException;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelperImpl implements AuthorizationHelper {

    @Override
    public Long getCurrentUserId() {
        try {
            CustomUserDetails authentication = (CustomUserDetails) getAuthentication().getPrincipal();
            return Objects.requireNonNull(authentication).getUserId();
        } catch (ClassCastException e) {
            throw new IllegalStateException("Principal 타입 오류: CustomUserDetails 변환에 실패했습니다.");
        }
    }

    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || Objects.requireNonNull(authentication.getPrincipal()).equals("anonymousUser")) {
            throw new UnAuthorizedException(UN_AUTHENTICATED);
        }
        return authentication;
    }
}