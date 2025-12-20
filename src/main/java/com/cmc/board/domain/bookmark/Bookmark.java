package com.cmc.board.domain.bookmark;

import static com.cmc.board.domain.constants.BookmarkExceptionStatus.BOOKMARK_REQUIRED_POST_ID;

import com.cmc.global.common.exception.client.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class Bookmark {
    private final Long id;
    private final Long postId;
    private final Long userId;

    public static Bookmark create(Long postId, Long userId) {
        if (postId == null) {
            throw new BadRequestException(BOOKMARK_REQUIRED_POST_ID);
        }
        return new Bookmark(null, postId, userId);
    }
}
