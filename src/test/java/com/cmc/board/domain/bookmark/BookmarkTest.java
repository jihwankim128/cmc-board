package com.cmc.board.domain.bookmark;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cmc.board.domain.constants.BookmarkExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;
import org.junit.jupiter.api.Test;

class BookmarkTest {

    @Test
    void 게시글_정보가_없으면_북마크를_생성할_수_없다() {
        // when & then
        assertThatThrownBy(() -> Bookmark.create(null, 1L))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", BookmarkExceptionStatus.BOOKMARK_REQUIRED_POST_ID);
    }
    
    @Test
    void 게시글_정보와_사용자_정보로_북마크_생성() {
        // when
        Bookmark bookmark = Bookmark.create(1L, 1L);

        // then
        assertThat(bookmark.getUserId()).isEqualTo(1L);
        assertThat(bookmark.getPostId()).isEqualTo(1L);
    }
}
