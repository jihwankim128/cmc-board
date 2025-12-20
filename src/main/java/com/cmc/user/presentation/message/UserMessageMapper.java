package com.cmc.user.presentation.message;

import static java.util.Map.entry;

import com.cmc.global.common.interfaces.StatusCode;
import com.cmc.global.web.message.MessageKeyMapper;
import com.cmc.user.domain.constants.UserExceptionStatus;
import com.cmc.user.presentation.api.status.UserSuccessStatus;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UserMessageMapper implements MessageKeyMapper {
    @Override
    public Map<StatusCode, String> getMessageKeys() {
        return Map.ofEntries(
                entry(UserSuccessStatus.USER_SIGNUP_SUCCESS, "user.signup.success"),
                entry(UserSuccessStatus.USER_LOGIN_SUCCESS, "user.login.success"),

                entry(UserExceptionStatus.USER_NICKNAME_EMPTY, "user.nickname.empty"),
                entry(UserExceptionStatus.USER_NICKNAME_OUT_OF_RANGE, "user.nickname.outOfRange"),
                entry(UserExceptionStatus.USER_EMAIL_INVALID, "user.email.invalid"),
                entry(UserExceptionStatus.USER_ACCOUNT_INVALID, "user.account.invalid"),
                entry(UserExceptionStatus.USER_ACCOUNT_DUPLICATED, "user.account.duplicated")
        );
    }
}
