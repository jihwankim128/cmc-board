package com.cmc.global.auth.filter;

import com.cmc.global.auth.AuthExceptionStatus;
import com.cmc.global.auth.annotation.AuthRole;
import com.cmc.global.auth.annotation.PreAuth;
import com.cmc.global.common.exception.client.ForbiddenException;
import com.cmc.global.common.exception.client.UnAuthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
@RequiredArgsConstructor
public class PreAuthFilter extends OncePerRequestFilter {

    private final RequestMappingHandlerMapping handlerMapping;

    private static boolean hasPermission(Set<AuthRole> requiredRoles, Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> matchesRequiredRole(requiredRoles, authority));
    }

    private static boolean matchesRequiredRole(Set<AuthRole> requiredRoles, String authority) {
        return requiredRoles.stream()
                .anyMatch(role -> switch (role) {
                    case ADMIN -> "ROLE_ADMIN".equals(authority);
                    case USER -> "ROLE_USER".equals(authority) || "ROLE_ADMIN".equals(authority);
                    case ANONYMOUS -> true;
                });
    }

    private static boolean isNotAuthenticated(Authentication auth) {
        return auth == null ||
                !auth.isAuthenticated() ||
                auth instanceof AnonymousAuthenticationToken;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        HandlerExecutionChain handlerChain;
        try {
            handlerChain = handlerMapping.getHandler(request);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        PreAuth preAuth = extractPreAuth(handlerChain);
        if (preAuth == null) {
            filterChain.doFilter(request, response);
            return;
        }

        checkAuthority(preAuth);
        filterChain.doFilter(request, response);
    }

    private PreAuth extractPreAuth(HandlerExecutionChain handlerChain) {
        if (handlerChain != null && handlerChain.getHandler() instanceof HandlerMethod handlerMethod) {
            PreAuth preAuth = handlerMethod.getMethodAnnotation(PreAuth.class);
            if (preAuth == null) {
                preAuth = handlerMethod.getBeanType().getAnnotation(PreAuth.class);
            }
            return preAuth;
        }
        return null;
    }

    private void checkAuthority(PreAuth preAuth) {
        Set<AuthRole> requiredRoles = Arrays.stream(preAuth.value()).collect(Collectors.toSet());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (requiredRoles.contains(AuthRole.ANONYMOUS)) {
            return;
        }
        if (isNotAuthenticated(auth)) {
            throw new UnAuthorizedException(AuthExceptionStatus.UN_AUTHENTICATED);
        }
        if (!hasPermission(requiredRoles, auth)) {
            throw new ForbiddenException(AuthExceptionStatus.HAS_NOT_AUTHORITY);
        }
    }
}