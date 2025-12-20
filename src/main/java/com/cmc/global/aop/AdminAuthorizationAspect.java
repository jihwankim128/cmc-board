package com.cmc.global.aop;

import static com.cmc.global.auth.AuthExceptionStatus.ADMIN_AUTHORITY;

import com.cmc.global.auth.AuthorizationHelper;
import com.cmc.global.common.exception.client.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AdminAuthorizationAspect {

    private final AuthorizationHelper authorizationHelper;

    @Around("@annotation(com.cmc.global.auth.AdminPrincipal)")
    public Object checkAdminRole(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!authorizationHelper.isAdmin()) {
            throw new ForbiddenException(ADMIN_AUTHORITY);
        }

        return joinPoint.proceed();
    }
}