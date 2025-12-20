package com.cmc.global.web.config;

import com.cmc.global.auth.AuthorizationHelper;
import com.cmc.global.web.interceptor.WebInterceptor;
import com.cmc.global.web.resolver.AdminPrincipalArgumentResolver;
import com.cmc.global.web.resolver.UserPrincipalArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final WebInterceptor webInterceptor;
    private final AuthorizationHelper authorizationHelper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/images/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserPrincipalArgumentResolver(authorizationHelper));
        resolvers.add(new AdminPrincipalArgumentResolver(authorizationHelper));
    }
}