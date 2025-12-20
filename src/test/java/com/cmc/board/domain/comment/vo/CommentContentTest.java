package com.cmc.board.domain.comment.vo;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cmc.board.domain.constants.CommentExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CommentContentTest {

    private static final int MAX_COMMENT_LENGTH = 255;

    @Test
    void 유효한_댓글_내용으로_객체를_생성한다() {
        // given
        String validContent = "이것은 유효한 댓글 내용입니다.";

        // when
        CommentContent content = new CommentContent(validContent);

        // then
        assertThat(content.value()).isEqualTo(validContent);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 댓글_내용이_비어있거나_공백이면_예외가_발생한다(String invalidContent) {
        assertThatThrownBy(() -> new CommentContent(invalidContent))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", CommentExceptionStatus.COMMENT_CONTENT_BLANK);
    }

    @Test
    void 댓글_내용이_최대_길이를_초과하면_예외가_발생한다() {
        // given
        String tooLongContent = "a".repeat(MAX_COMMENT_LENGTH + 1);

        // when & then
        assertThatThrownBy(() -> new CommentContent(tooLongContent))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", CommentExceptionStatus.COMMENT_CONTENT_TOO_LONG);
    }
}