package com.cmc.board.application;

import com.cmc.board.application.port.in.CreateBookmarkUseCase;
import com.cmc.board.application.port.out.ValidatePostPort;
import com.cmc.board.domain.bookmark.Bookmark;
import com.cmc.board.domain.bookmark.BookmarkRepository;
import com.cmc.board.domain.exception.DuplicatedBookmarkException;
import com.cmc.board.domain.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService implements CreateBookmarkUseCase {

    private final BookmarkRepository bookmarkRepository;
    private final ValidatePostPort validatePostPort;

    @Override
    public void create(Long postId, Long userId) {
        if (!validatePostPort.existsById(postId)) {
            throw new PostNotFoundException();
        }
        if (bookmarkRepository.hasBookmark(postId, userId)) {
            throw new DuplicatedBookmarkException();
        }
        Bookmark bookmark = Bookmark.create(postId, userId);
        bookmarkRepository.save(bookmark);
    }
}
