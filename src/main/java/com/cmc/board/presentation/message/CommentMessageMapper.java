package com.cmc.board.presentation.message;

import static java.util.Map.entry;

import com.cmc.board.domain.constants.CommentExceptionStatus;
import com.cmc.board.presentation.api.status.CommentSuccessStatus;
import com.cmc.global.common.interfaces.StatusCode;
import com.cmc.global.web.message.MessageKeyMapper;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CommentMessageMapper implements MessageKeyMapper {
    @Override
    public Map<StatusCode, String> getMessageKeys() {
        return Map.ofEntries(
                entry(CommentSuccessStatus.CREATE_COMMENT_SUCCESS, "comment.create.success"),
                entry(CommentSuccessStatus.CREATE_REPLY_SUCCESS, "comment.reply.create.success"),
                entry(CommentSuccessStatus.UPDATE_COMMENT_SUCCESS, "comment.update.success"),
                entry(CommentSuccessStatus.DELETE_COMMENT_SUCCESS, "comment.delete.success"),

                entry(CommentExceptionStatus.COMMENT_AUTHOR_NOT_NULL, "comment.author.notNull"),
                entry(CommentExceptionStatus.COMMENT_POST_NOT_NULL, "comment.post.notNull"),
                entry(CommentExceptionStatus.COMMENT_CONTENT_BLANK, "comment.content.blank"),
                entry(CommentExceptionStatus.COMMENT_CONTENT_TOO_LONG, "comment.content.tooLong"),
                entry(CommentExceptionStatus.CANNOT_REPLY_TO_DELETED_COMMENT, "comment.reply.toDeleted"),
                entry(CommentExceptionStatus.COMMENT_DEPTH_EXCEED, "comment.depth.exceed"),
                entry(CommentExceptionStatus.COMMENT_NOT_FOUND, "comment.not.found"),
                entry(CommentExceptionStatus.MISMATCH_COMMENT_AUTHOR, "comment.author.mismatch")
        );
    }
}
