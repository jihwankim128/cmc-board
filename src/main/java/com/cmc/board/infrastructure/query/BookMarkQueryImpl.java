package com.cmc.board.infrastructure.query;

import com.cmc.board.infrastructure.database.jpa.BookmarkJpaRepository;
import com.cmc.board.infrastructure.database.jpa.entity.BookmarkEntity;
import com.cmc.board.infrastructure.query.dto.BookmarkDto;
import com.cmc.board.infrastructure.query.dto.PostDto;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookMarkQueryImpl implements BookmarkQuery {

    private final BookmarkJpaRepository jpaRepository;
    private final PostQuery postQuery;

    @Override
    public List<BookmarkDto> getBookmarks() {
        List<BookmarkEntity> bookmarks = jpaRepository.findAll();
        List<Long> postIds = bookmarks.stream().map(BookmarkEntity::getPostId).toList();
        Map<Long, PostDto> posts = postQuery.getPosts(postIds);
        return jpaRepository.findAll().stream()
                .map(bookmark -> new BookmarkDto(
                        bookmark.getId(),
                        posts.get(bookmark.getPostId()),
                        bookmark.getCreatedAt()
                ))
                .toList();
    }

    @Override
    public boolean isBookmarked(Long postId, Long userId) {
        return jpaRepository.existsByPostIdAndUserId(postId, userId);
    }
}
