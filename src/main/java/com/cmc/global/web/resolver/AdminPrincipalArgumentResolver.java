package com.cmc.global.web.resolver;


import static com.cmc.global.auth.AuthExceptionStatus.ADMIN_AUTHORITY;

import com.cmc.global.auth.AdminPrincipal;
import com.cmc.global.auth.AuthorizationHelper;
import com.cmc.global.common.exception.client.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AdminPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthorizationHelper authorizationHelper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AdminPrincipal.class) &&
                parameter.getParameterType().equals(Long.class);
    }

    @Override
    public @Nullable Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        if (!authorizationHelper.isAdmin()) {
            throw new ForbiddenException(ADMIN_AUTHORITY);
        }
        return authorizationHelper.getCurrentUserId();
    }
}