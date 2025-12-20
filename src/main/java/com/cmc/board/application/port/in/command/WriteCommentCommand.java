package com.cmc.board.application.port.in.command;

import com.cmc.board.domain.comment.Comment;
import com.cmc.board.domain.comment.vo.CommentContent;

public record WriteCommentCommand(Long authorId, Long postId, String content) {
    public Comment toComment() {
        return Comment.create(authorId, postId, new CommentContent(content));
    }
}
