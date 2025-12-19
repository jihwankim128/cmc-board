package com.cmc.global.web.constants;

public enum BeanValidationConstants {
    REQUEST_FIELD_NOT_VALID("error.validation.invalidRequestField"),
    REQUEST_PARAM_NOT_VALID("error.validation.invalidRequestParam"),
    REQUEST_NOT_READABLE("error.validation.notReadable"),
    METHOD_ARGUMENT_TYPE_MISMATCH("error.validation.argumentTypeMismatch"),
    MISSING_REQUEST_PARAMETER("error.validation.missingParameter"),
    ;

    private final String messageKey;

    BeanValidationConstants(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
