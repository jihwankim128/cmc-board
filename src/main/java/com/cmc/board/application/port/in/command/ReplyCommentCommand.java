package com.cmc.board.application.port.in.command;

import com.cmc.board.domain.comment.Comment;
import com.cmc.board.domain.comment.vo.CommentContent;

public record ReplyCommentCommand(Long authorId, Long parentCommentId, String content) {

    public Comment toReply(Comment parent) {
        return Comment.reply(authorId, parent, new CommentContent(content));
    }
}
