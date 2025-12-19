package com.cmc.global.web.message;

import com.cmc.global.common.interfaces.StatusCode;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GlobalMessageKeyMapper {

    private final Map<StatusCode, String> globalMessageKeys;

    public GlobalMessageKeyMapper(List<MessageKeyMapper> mappers) {
        Map<StatusCode, String> globalMessageKeys = new HashMap<>();
        mappers.forEach(mapper -> globalMessageKeys.putAll(mapper.getMessageKeys()));
        this.globalMessageKeys = Collections.unmodifiableMap(globalMessageKeys);
    }

    public String getMessageKey(StatusCode status) {
        return globalMessageKeys.get(status);
    }
}
