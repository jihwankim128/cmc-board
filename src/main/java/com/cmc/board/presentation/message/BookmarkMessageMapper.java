package com.cmc.board.presentation.message;

import static java.util.Map.entry;

import com.cmc.board.domain.constants.BookmarkExceptionStatus;
import com.cmc.board.presentation.api.status.BookmarkSuccessStatus;
import com.cmc.global.common.interfaces.StatusCode;
import com.cmc.global.web.message.MessageKeyMapper;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMessageMapper implements MessageKeyMapper {
    @Override
    public Map<StatusCode, String> getMessageKeys() {
        return Map.ofEntries(
                entry(BookmarkSuccessStatus.CREATE_BOOKMARK_SUCCESS, "bookmark.create.success"),

                entry(BookmarkExceptionStatus.BOOKMARK_REQUIRED_POST_ID, "bookmark.required.post")
        );
    }
}
