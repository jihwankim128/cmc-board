package com.cmc.global.web.exception;

import com.cmc.global.common.exception.client.UnAuthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalWebExceptionHandler {

    @ExceptionHandler(UnAuthorizedException.class)
    public String handleException(UnAuthorizedException e) {
        return "user/login";
    }
}
