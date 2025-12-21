package com.cmc.board.domain.bookmark;

public interface BookmarkRepository {

    Bookmark save(Bookmark bookmark);

    boolean hasBookmark(Long postId, Long userId);
}
