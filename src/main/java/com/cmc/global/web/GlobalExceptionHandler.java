package com.cmc.global.web;

import com.cmc.global.common.exception.BusinessException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String VALID_EXCEPTION_FORMAT = "유효성 검증 실패: %s";
    private static final String MISSING_PARAMETER_EXCEPTION_FORMAT = "필드 %s(은)는 자료형 %s 타입이어야 합니다.";
    private static final String TYPE_MISS_MATCH_EXCEPTION_FORMAT = "%s (으)로 변환할 수 없는 요청입니다.";

    @ExceptionHandler(BusinessException.class)
    public ApiTemplate<String> handleBusinessException(BusinessException e) {
        log.warn("BusinessException - {}: {}", e.getStatus().getCode(), e.getMessage());
        return ApiTemplate.from(e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiTemplate<List<String>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<String> errors = e.getAllErrors()
                .stream()
                .map(mr -> VALID_EXCEPTION_FORMAT.formatted(mr.getDefaultMessage()))
                .toList();
        log.warn("[{}]: {}", e.getClass().getSimpleName(), errors);
        return ApiTemplate.of(GlobalExceptionStatus.ARGUMENT_NOT_VALID, errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ApiTemplate<List<String>> handleMethodValidation(HandlerMethodValidationException e) {
        List<String> errors = e.getAllErrors()
                .stream()
                .map(mr -> VALID_EXCEPTION_FORMAT.formatted(mr.getDefaultMessage()))
                .toList();
        log.warn("[{}]: {}", e.getClass().getSimpleName(), errors);
        return ApiTemplate.of(GlobalExceptionStatus.ARGUMENT_NOT_VALID, errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiTemplate<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        String error = Optional.ofNullable(e.getRequiredType())
                .map(clazz -> TYPE_MISS_MATCH_EXCEPTION_FORMAT.formatted(clazz.getSimpleName()))
                .orElse("Unknown Argument");
        log.warn("[{}] - {} : {}", e.getClass().getSimpleName(), e.getName(), error, e);
        return ApiTemplate.of(GlobalExceptionStatus.ARGUMENT_TYPE_MISMATCH, error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiTemplate<String> handleMissingRequestParameter(MissingServletRequestParameterException e) {
        String error = MISSING_PARAMETER_EXCEPTION_FORMAT.formatted(e.getParameterName(), e.getParameterType());
        log.warn("{} : {}", e.getClass().getSimpleName(), error, e);
        return ApiTemplate.of(GlobalExceptionStatus.MISSING_PARAMETER, error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiTemplate<String> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.warn("{} : {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return ApiTemplate.from(GlobalExceptionStatus.DATA_NOT_READABLE);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ApiTemplate<String> handleNoResourceFound() {
        return ApiTemplate.from(GlobalExceptionStatus.NO_RESOURCE);
    }

    @ExceptionHandler(Exception.class)
    public ApiTemplate<String> handleException(Exception e) {
        log.error("{}: ", e.getClass().getSimpleName(), e);
        return ApiTemplate.from(GlobalExceptionStatus.INTERNAL_SERVER_ERROR);
    }
}
