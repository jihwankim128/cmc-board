package com.cmc.board.domain.post.vo;

import static com.cmc.board.domain.constants.PostExceptionStatus.POST_CONTENT_BLANK;
import static com.cmc.board.domain.constants.PostExceptionStatus.POST_CONTENT_TOO_SHORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cmc.global.common.exception.client.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PostContentTest {

    @Test
    void 유효한_게시글_내용을_생성할_수_있다() {
        // given
        String validContent = "이것은 유효한 게시글 내용입니다.";

        // when
        PostContent postContent = new PostContent(validContent);

        // then
        assertThat(postContent.value()).isEqualTo(validContent);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 게시글_내용이_비어있으면_예외가_발생한다(String emptyContent) {
        // when & then
        assertThatThrownBy(() -> new PostContent(emptyContent))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", POST_CONTENT_BLANK);
    }

    @Test
    void 게시글_내용이_5자_미만이면_예외가_발생한다() {
        // given
        String shortContent = "1".repeat(4);
        // when & then
        assertThatThrownBy(() -> new PostContent(shortContent))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", POST_CONTENT_TOO_SHORT);
    }
}