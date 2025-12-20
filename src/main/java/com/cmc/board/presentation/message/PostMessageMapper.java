package com.cmc.board.presentation.message;

import com.cmc.board.domain.constants.PostExceptionStatus;
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
                PostSuccessStatus.CREATE_POST_SUCCESS, "post.create.success",
                PostSuccessStatus.UPDATE_POST_SUCCESS, "post.update.success",
                PostExceptionStatus.POST_TITLE_BLANK, "post.title.blank",
                PostExceptionStatus.POST_CONTENT_BLANK, "post.content.blank",
                PostExceptionStatus.POST_TITLE_TOO_LONG, "post.title.tooLong",
                PostExceptionStatus.POST_CONTENT_TOO_SHORT, "post.content.tooShort",
                PostExceptionStatus.MISMATCH_POST_AUTHOR, "post.author.mismatch",
                PostExceptionStatus.NOT_FOUND_CATEGORY, "post.category.notFound",
                PostExceptionStatus.NOT_FOUND_POST, "post.not.found"
        );
    }
}
