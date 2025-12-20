package com.cmc.board.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cmc.board.application.port.out.ValidatePostPort;
import com.cmc.board.domain.bookmark.Bookmark;
import com.cmc.board.domain.bookmark.BookmarkRepository;
import com.cmc.board.domain.constants.PostExceptionStatus;
import com.cmc.board.domain.exception.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookmarkServiceTest {

    @Mock
    private BookmarkRepository bookmarkRepository;
    @Mock
    private ValidatePostPort validatePostPort;
    @InjectMocks
    private BookmarkService bookmarkService;

    @Test
    void 없는_게시글을_북마크로_정하면_예외가_발생한다() {
        // given
        when(validatePostPort.existsById(anyLong())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> bookmarkService.create(1L, 1L))
                .isInstanceOf(PostNotFoundException.class)
                .hasFieldOrPropertyWithValue("status", PostExceptionStatus.POST_NOT_FOUND);
    }

    @Test
    void 북마크_생성_커맨드가_발생하면_북마크를_생성하고_저장한다() {
        // given
        when(validatePostPort.existsById(anyLong())).thenReturn(true);

        // when
        bookmarkService.create(1L, 1L);

        // then
        verify(bookmarkRepository, times(1)).save(any(Bookmark.class));
    }
}