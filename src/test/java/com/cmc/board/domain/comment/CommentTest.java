package com.cmc.board.domain.comment;

import static org.assertj.core.api.Assertions.assertThat;

import com.cmc.board.domain.comment.vo.CommentContent;
import com.cmc.board.domain.comment.vo.CommentDepth;
import org.junit.jupiter.api.Test;

class CommentTest {

    private Long postId = 1L;
    private Long authorId = 1L;
    private CommentContent initialContent = new CommentContent("초기 댓글 내용입니다.");

    @Test
    void 첫_댓글_생성시_깊이는_0이다() {
        // when
        Comment comment = Comment.create(authorId, postId, initialContent);

        // then
        assertThat(comment.getDepth()).isEqualTo(CommentDepth.BASE_DEPTH);
        assertThat(comment.getAuthorId()).isEqualTo(authorId);
        assertThat(comment.getPostId()).isEqualTo(postId);
        assertThat(comment.getContent()).isEqualTo(initialContent);
    }

}