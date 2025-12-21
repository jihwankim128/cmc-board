package com.cmc.board.infrastructure.database;

import com.cmc.board.domain.bookmark.Bookmark;
import com.cmc.board.domain.bookmark.BookmarkRepository;
import com.cmc.board.infrastructure.database.jpa.BookmarkJpaRepository;
import com.cmc.board.infrastructure.database.jpa.entity.BookmarkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepository {

    private final BookmarkJpaRepository jpaRepository;

    @Override
    public Bookmark save(Bookmark bookmark) {
        BookmarkEntity saved = jpaRepository.save(BookmarkEntity.from(bookmark));
        return saved.toDomain();
    }

    @Override
    public boolean hasBookmark(Long postId, Long userId) {
        return jpaRepository.existsByPostIdAndUserId(postId, userId);
    }
}
