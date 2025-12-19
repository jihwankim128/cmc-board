package com.cmc.global.web.interceptor;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Slf4j
public class WebInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            @Nullable Exception ex
    ) throws Exception {
        if (!(response instanceof ContentCachingResponseWrapper wrapper)) {
            return;
        }

        String responseBody = new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        captureLog(response.getStatus(), request.getRequestURI(), responseBody);

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private void captureLog(int status, String uri, String responseBody) {
        switch (status / 100) {
            case 5 -> log.error("[Server Error] URI: {}, Status: {}", uri, status);
            case 4 -> log.warn("[Client Error] URI: {}, Status: {}, Response: {}", uri, status, responseBody);
            default -> log.debug("[Standard] URI: {}, Status: {}", uri, status);
        }
    }
}