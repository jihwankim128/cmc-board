package com.cmc.global.web.exception;

import static com.cmc.global.web.constants.BeanValidationConstants.METHOD_ARGUMENT_TYPE_MISMATCH;
import static com.cmc.global.web.constants.BeanValidationConstants.MISSING_REQUEST_PARAMETER;
import static com.cmc.global.web.constants.BeanValidationConstants.REQUEST_FIELD_NOT_VALID;
import static com.cmc.global.web.constants.BeanValidationConstants.REQUEST_NOT_READABLE;
import static com.cmc.global.web.constants.BeanValidationConstants.REQUEST_PARAM_NOT_VALID;

import com.cmc.global.common.dto.ErrorDetail;
import com.cmc.global.common.dto.ErrorResponse;
import com.cmc.global.web.message.MessageSourceHelper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class BeanValidationExceptionHandler {

    private final MessageSourceHelper messageSourceHelper;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ErrorResponse response = ErrorResponse.of(
                REQUEST_FIELD_NOT_VALID.name(),
                messageSourceHelper.extractMessage(REQUEST_FIELD_NOT_VALID.getMessageKey()),
                e.getBindingResult().getFieldErrors().stream()
                        .map(fieldError -> new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
                        .toList()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleMethodValidation(HandlerMethodValidationException e) {
        List<ErrorDetail> details = e.getParameterValidationResults()
                .stream()
                .flatMap(vr -> vr.getResolvableErrors()
                        .stream()
                        .map(re -> new ErrorDetail(vr.getMethodParameter().getParameterName(), re.getDefaultMessage()))
                )
                .toList();

        ErrorResponse response = ErrorResponse.of(
                REQUEST_PARAM_NOT_VALID.name(),
                messageSourceHelper.extractMessage(REQUEST_PARAM_NOT_VALID.getMessageKey()),
                details
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        ErrorResponse response = ErrorResponse.of(
                REQUEST_NOT_READABLE.name(),
                messageSourceHelper.extractMessage(REQUEST_NOT_READABLE.getMessageKey())
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.error("{}, {}, {}, {}", e.getValue(), e.getParameter().getParameterName(), e.getPropertyName(),
                e.getMessage());
        String detailMessage = messageSourceHelper.extractMessage(
                "parameter.type.mismatch",
                e.getParameter().getParameterName(),
                e.getParameter().getParameterType().getSimpleName(),
                String.valueOf(e.getValue())
        );
        ErrorResponse response = ErrorResponse.of(
                METHOD_ARGUMENT_TYPE_MISMATCH.name(),
                messageSourceHelper.extractMessage(METHOD_ARGUMENT_TYPE_MISMATCH.getMessageKey()),
                List.of(new ErrorDetail(e.getName(), detailMessage))
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestParameter(MissingServletRequestParameterException e) {
        String detailMessage = messageSourceHelper.extractMessage(
                "parameter.type.missing",
                e.getParameterName(),
                e.getParameterType()
        );
        ErrorResponse response = ErrorResponse.of(
                MISSING_REQUEST_PARAMETER.name(),
                messageSourceHelper.extractMessage(MISSING_REQUEST_PARAMETER.getMessageKey()),
                List.of(new ErrorDetail(e.getParameterName(), detailMessage))
        );
        return ResponseEntity.badRequest().body(response);
    }
}
