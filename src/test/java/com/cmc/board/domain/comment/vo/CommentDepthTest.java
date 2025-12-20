package com.cmc.board.domain.comment.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cmc.board.domain.constants.CommentExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;
import org.junit.jupiter.api.Test;

class CommentDepthTest {

    @Test
    void 깊이는_음수일_수_없다() {
        // given
        int negativeDepth = -1;

        // when & then
        assertThatThrownBy(() -> new CommentDepth(negativeDepth))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 현재_댓글_깊이를_알면_다음_깊이를_알_수_있다() {
        // given
        CommentDepth now = CommentDepth.BASE_DEPTH;

        // when
        CommentDepth next = now.child();

        // then
        assertThat(next.value()).isEqualTo(now.value() + 1);
    }

    @Test
    void 댓글은_최대_깊이인_2를_넘을_수_없다() {
        // given
        CommentDepth limitDepth = new CommentDepth(1);

        // when & then
        assertThatThrownBy(() -> limitDepth.child())
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", CommentExceptionStatus.COMMENT_DEPTH_EXCEED);
    }
}