package com.cmc.board.presentation.message;

import static java.util.Map.entry;

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
        return Map.ofEntries(
                entry(PostSuccessStatus.CREATE_POST_SUCCESS, "post.create.success"),
                entry(PostSuccessStatus.UPDATE_POST_SUCCESS, "post.update.success"),
                entry(PostSuccessStatus.DELETE_POST_SUCCESS, "post.delete.success"),
                entry(PostSuccessStatus.GET_POSTS_SUCCESS, "post.get.posts.success"),
                entry(PostSuccessStatus.GET_POST_SUCCESS, "post.get.post.success"),

                entry(PostExceptionStatus.POST_TITLE_BLANK, "post.title.blank"),
                entry(PostExceptionStatus.POST_CONTENT_BLANK, "post.content.blank"),
                entry(PostExceptionStatus.POST_TITLE_TOO_LONG, "post.title.tooLong"),
                entry(PostExceptionStatus.POST_CONTENT_TOO_SHORT, "post.content.tooShort"),
                entry(PostExceptionStatus.MISMATCH_POST_AUTHOR, "post.author.mismatch"),
                entry(PostExceptionStatus.POST_NOT_FOUND, "post.not.found")
        );
    }
}
