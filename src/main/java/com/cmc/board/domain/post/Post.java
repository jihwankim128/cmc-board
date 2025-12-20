package com.cmc.board.domain.post;

import com.cmc.board.domain.constants.PostExceptionStatus;
import com.cmc.board.domain.post.vo.PostContent;
import com.cmc.board.domain.post.vo.PostTitle;
import com.cmc.global.common.exception.client.UnAuthorizedException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class Post {
    private final Long id;
    private final Long authorId;
    private Long categoryId;
    private PostTitle title;
    private PostContent content;
    private PostStatus status;

    private Post(Long id, Long authorId, Long categoryId, PostTitle title, PostContent content) {
        this.id = id;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.status = PostStatus.PUBLISHED;
    }

    public static Post create(Long userId, Long categoryId, PostTitle title, PostContent content) {
        return new Post(null, userId, categoryId, title, content);
    }

    public void update(Long userId, Long categoryId, PostTitle title, PostContent content) {
        validateAuthor(userId);
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
    }

    public void delete(Long userId) {
        validateAuthor(userId);
        this.status = PostStatus.DELETED;
    }

    private void validateAuthor(Long userId) {
        if (!authorId.equals(userId)) {
            throw new UnAuthorizedException(PostExceptionStatus.MISMATCH_POST_AUTHOR);
        }
    }
}
