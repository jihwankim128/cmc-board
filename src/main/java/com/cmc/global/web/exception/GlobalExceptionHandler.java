package com.cmc.global.web.exception;

import com.cmc.global.common.dto.ErrorResponse;
import com.cmc.global.common.exception.BaseException;
import com.cmc.global.common.exception.client.ForbiddenException;
import com.cmc.global.common.exception.client.NotFoundException;
import com.cmc.global.common.exception.client.UnAuthorizedException;
import com.cmc.global.web.message.MessageSourceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_KEY = "error.5xx.internal";

    private final MessageSourceHelper messageSourceHelper;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleClientBaseException(BaseException e) {
        String message = messageSourceHelper.extractMessage(e.getStatus(), e.getArgs());
        return ResponseEntity.status(determineHttpStatus(e))
                .body(ErrorResponse.of(e.getStatus().getCode(), message));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedRuntimeException(RuntimeException e) {
        log.error("예상하지 못한 서버 에러: ", e);
        String message = messageSourceHelper.extractMessage(INTERNAL_SERVER_ERROR_KEY);
        return ResponseEntity.status(500)
                .body(ErrorResponse.of("INTERNAL_SERVER_ERROR", message));
    }

    private int determineHttpStatus(BaseException e) {
        return switch (e) {
            case UnAuthorizedException ignored -> 401;
            case ForbiddenException ignored -> 403;
            case NotFoundException ignored -> 404;
            default -> 400;
        };
    }
}
