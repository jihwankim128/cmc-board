package com.cmc.board.domain.comment;

import static com.cmc.board.domain.comment.vo.CommentDepth.BASE_DEPTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cmc.board.domain.comment.vo.CommentContent;
import com.cmc.board.domain.constants.CommentExceptionStatus;
import com.cmc.global.common.exception.client.ForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommentTest {

    private Long postId = 1L;
    private Long authorId = 1L;
    private CommentContent initialContent = new CommentContent("초기 댓글 내용입니다.");
    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = Comment.create(authorId, postId, initialContent);
    }

    @Test
    void 첫_댓글_생성시_깊이는_0이다() {
        // given & when & then
        assertThat(comment.getDepth()).isEqualTo(BASE_DEPTH);
        assertThat(comment.getAuthorId()).isEqualTo(authorId);
        assertThat(comment.getPostId()).isEqualTo(postId);
        assertThat(comment.getContent()).isEqualTo(initialContent);
    }

    @Test
    void 댓글을_수정할_수_있다() {
        // given
        CommentContent newContent = new CommentContent("새로운 내용");

        // when
        comment.update(authorId, newContent);

        // then
        assertThat(comment.getContent()).isEqualTo(newContent);
    }

    @Test
    void 댓글_수정시_본인이_아니면_예외가_발생한다() {
        // given
        Long otherUserId = 2L;
        CommentContent newContent = new CommentContent("새로운 내용");

        // when & then
        assertThatThrownBy(() -> comment.update(otherUserId, newContent))
                .isInstanceOf(ForbiddenException.class)
                .hasFieldOrPropertyWithValue("status", CommentExceptionStatus.MISMATCH_COMMENT_AUTHOR);
    }

}