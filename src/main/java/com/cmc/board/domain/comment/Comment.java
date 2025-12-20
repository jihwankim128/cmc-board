package com.cmc.board.domain.comment;

import static com.cmc.board.domain.constants.CommentExceptionStatus.MISMATCH_COMMENT_AUTHOR;

import com.cmc.board.domain.comment.vo.CommentContent;
import com.cmc.board.domain.comment.vo.CommentDepth;
import com.cmc.global.common.exception.client.ForbiddenException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class Comment {
    private final Long id;
    private final Long postId;
    private final Long parentId;
    private final Long authorId;
    private final CommentDepth depth;
    private CommentContent content;
    private CommentStatus status;

    private Comment(Long id, Long postId, Long parentId, Long authorId, CommentDepth depth, CommentContent content) {
        this.id = id;
        this.postId = postId;
        this.parentId = parentId;
        this.authorId = authorId;
        this.depth = depth;
        this.content = content;
        this.status = CommentStatus.PUBLISHED;
    }

    public static Comment create(Long authorId, Long postId, CommentContent content) {
        return new Comment(null, postId, null, authorId, CommentDepth.BASE_DEPTH, content);
    }

    public void update(Long userId, CommentContent newContent) {
        validateAuthor(userId);
        this.content = newContent;
    }

    private void validateAuthor(Long userId) {
        if (!authorId.equals(userId)) {
            throw new ForbiddenException(MISMATCH_COMMENT_AUTHOR);
        }
    }
}
