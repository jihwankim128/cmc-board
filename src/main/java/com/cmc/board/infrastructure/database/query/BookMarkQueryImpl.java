package com.cmc.board.infrastructure.database.query;

import com.cmc.board.infrastructure.database.jpa.BookmarkJpaRepository;
import com.cmc.board.presentation.query.BookmarkQuery;
import com.cmc.board.presentation.query.dto.BookmarkDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookMarkQueryImpl implements BookmarkQuery {

    private final BookmarkJpaRepository jpaRepository;

    @Override
    public List<BookmarkDto> getBookmarks() {
        return jpaRepository.findAll().stream()
                .map(bookmark -> new BookmarkDto(
                        bookmark.getId(),
                        bookmark.getPostId(),
                        bookmark.getUserId(),
                        bookmark.getCreatedAt()
                ))
                .toList();
    }
}
