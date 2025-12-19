package com.cmc.global.web.message;

import com.cmc.global.common.interfaces.StatusCode;
import java.util.Map;

public interface MessageKeyMapper {

    Map<StatusCode, String> getMessageKeys();
}
