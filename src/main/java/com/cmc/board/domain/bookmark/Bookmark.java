package com.cmc.board.domain.bookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class Bookmark {
    private final Long id;
    private final Long postId;
    private final Long userId;

    public static Bookmark create(Long postId, Long userId) {
        return new Bookmark(null, postId, userId);
    }
}
