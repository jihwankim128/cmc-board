package com.cmc.board.presentation.message;

import com.cmc.board.presentation.api.status.PostSuccessStatus;
import com.cmc.global.common.interfaces.StatusCode;
import com.cmc.global.web.message.MessageKeyMapper;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PostMessageMapper implements MessageKeyMapper {
    @Override
    public Map<StatusCode, String> getMessageKeys() {
        return Map.of(
                PostSuccessStatus.CREATE_POST_SUCCESS, "post.create.success"
        );
    }
}
