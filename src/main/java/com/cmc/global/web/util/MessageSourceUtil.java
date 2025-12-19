package com.cmc.global.web.util;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSourceUtil {

    private final MessageSource messageSource;

    public String extractMessage(String messageKey) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, null, locale);
    }

    public String extractMessage(String messageKey, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, args, locale);
    }
}
