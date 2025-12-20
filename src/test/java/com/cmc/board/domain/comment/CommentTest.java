package com.cmc.board.domain.comment;

import static com.cmc.board.domain.comment.CommentStatus.PUBLISHED;
import static com.cmc.board.domain.comment.vo.CommentDepth.BASE_DEPTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cmc.board.domain.comment.vo.CommentContent;
import com.cmc.board.domain.constants.CommentExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;
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
    void 댓글_작성시_게시글_작성자_없으면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> Comment.create(null, postId, initialContent))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", CommentExceptionStatus.COMMENT_AUTHOR_NOT_NULL);
    }

    @Test
    void 댓글_작성시_게시글_식별자가_없으면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> Comment.create(authorId, null, initialContent))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", CommentExceptionStatus.COMMENT_POST_NOT_NULL);
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

    @Test
    void 댓글_삭제를_할_수_있다() {
        // when
        comment.delete(authorId);

        // then
        assertThat(comment.getStatus()).isEqualTo(CommentStatus.DELETED);
    }

    @Test
    void 댓글_삭제_시_본인이_아니면_예외가_발생한다() {
        // given
        Long otherUserId = 2L;
        // when & then
        assertThatThrownBy(() -> comment.delete(otherUserId))
                .isInstanceOf(ForbiddenException.class)
                .hasFieldOrPropertyWithValue("status", CommentExceptionStatus.MISMATCH_COMMENT_AUTHOR);
    }

    @Test
    void 댓글에_답글을_달면_댓글보다_깊이가_증가하고_댓글의_정보를_가져온다() {
        // given
        Comment parent = Comment.of(1L, 1L, null, authorId, BASE_DEPTH, initialContent, PUBLISHED);

        // when
        Comment reply = Comment.reply(authorId, parent, initialContent);

        // then
        assertThat(reply.getDepth()).isEqualTo(parent.getDepth().child());
        assertThat(reply.getPostId()).isEqualTo(parent.getPostId());
        assertThat(reply.getParentId()).isEqualTo(parent.getId());
    }

    @Test
    void 삭제된_댓글에_답글을_달면_예외가_발생한다() {
        // given
        Comment parent = Comment.of(1L, 1L, null, authorId, BASE_DEPTH, initialContent, PUBLISHED);
        parent.delete(1L);

        // when & then
        assertThatThrownBy(() -> Comment.reply(authorId, parent, initialContent))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", CommentExceptionStatus.CANNOT_REPLY_TO_DELETED_COMMENT);
    }
}