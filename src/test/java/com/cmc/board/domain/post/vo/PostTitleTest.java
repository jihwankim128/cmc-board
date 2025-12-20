package com.cmc.board.domain.post.vo;

import static com.cmc.board.domain.constants.PostExceptionStatus.POST_TITLE_BLANK;
import static com.cmc.board.domain.constants.PostExceptionStatus.POST_TITLE_TOO_LONG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cmc.global.common.exception.client.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PostTitleTest {

    @Test
    void 유효한_게시글_제목을_생성할_수_있다() {
        // given
        String validTitle = "유효한 게시글 제목";

        // when
        PostTitle postTitle = new PostTitle(validTitle);

        // then
        assertThat(postTitle.value()).isEqualTo(validTitle);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 게시글_제목이_비어있으면_예외가_발생한다(String emptyTitle) {
        // when & then
        assertThatThrownBy(() -> new PostTitle(emptyTitle))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", POST_TITLE_BLANK);
    }

    @Test
    void 게시글_제목이_50자를_초과하면_예외가_발생한다() {
        // given
        String longTitle = "a".repeat(51);

        // when & then
        assertThatThrownBy(() -> new PostTitle(longTitle))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", POST_TITLE_TOO_LONG);
    }
}