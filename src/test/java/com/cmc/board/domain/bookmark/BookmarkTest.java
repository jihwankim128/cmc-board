package com.cmc.board.domain.bookmark;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BookmarkTest {

    @Test
    void 게시글_정보와_사용자_정보로_북마크_생성() {
        // when
        Bookmark bookmark = Bookmark.create(1L, 1L);

        // then
        assertThat(bookmark.getUserId()).isEqualTo(1L);
        assertThat(bookmark.getPostId()).isEqualTo(1L);
    }
}
