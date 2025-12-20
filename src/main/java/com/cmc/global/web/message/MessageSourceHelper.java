package com.cmc.global.web.message;

import com.cmc.global.common.interfaces.StatusCode;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSourceHelper {

    private final MessageSource messageSource;
    private final GlobalMessageKeyMapper globalMessageKeyMapper;

    public String extractMessage(String messageKey) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, null, locale);
    }

    public String extractMessage(String messageKey, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, args, locale);
    }

    public String extractMessage(StatusCode statusCode) {
        String messageKey = globalMessageKeyMapper.getMessageKey(statusCode);
        return extractMessage(messageKey);
    }

    public String extractMessage(StatusCode statusCode, Object... args) {
        String messageKey = globalMessageKeyMapper.getMessageKey(statusCode);
        return extractMessage(messageKey, args);
    }
}
