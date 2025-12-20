package com.cmc.global.auth;

import com.cmc.global.common.interfaces.StatusCode;
import com.cmc.global.web.message.MessageKeyMapper;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AuthMessageKeyMapper implements MessageKeyMapper {

    @Override
    public Map<StatusCode, String> getMessageKeys() {
        return Map.of(
                AuthExceptionStatus.ADMIN_AUTHORITY, "auth.not.admin",
                AuthExceptionStatus.UN_AUTHENTICATED, "auth.un.authenticated"
        );
    }
}
