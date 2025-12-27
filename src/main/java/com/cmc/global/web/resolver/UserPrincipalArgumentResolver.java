package com.cmc.global.web.resolver;

import com.cmc.global.auth.AuthorizationHelper;
import com.cmc.global.auth.annotation.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class UserPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthorizationHelper authorizationHelper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserPrincipal.class) &&
                parameter.getParameterType().equals(Long.class);
    }

    @Override
    public @Nullable Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        if (authorizationHelper.isAnonymous()) {
            return null;
        }
        return authorizationHelper.getCurrentUserId();
    }
}
