package com.cmc.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] SWAGGER_URIS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
    };

    private static final String[] STATIC_URIS = {
            "/js/**",
            "/css/**"
    };

    private static final String[] PAGE_SERVING_URIS = {
            "/",
            "/login",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        return http.httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PAGE_SERVING_URIS).permitAll()
                        .requestMatchers(STATIC_URIS).permitAll()
                        .requestMatchers("/account/**").permitAll()
                        .requestMatchers("/tool/**").permitAll()
                        .requestMatchers(SWAGGER_URIS).hasRole("ADMIN")
                        .anyRequest().authenticated())
                .securityContext(context -> context.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .build();
    }
}