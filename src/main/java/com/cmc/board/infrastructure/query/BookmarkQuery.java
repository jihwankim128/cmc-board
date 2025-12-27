package com.cmc.board.infrastructure.query;

import com.cmc.board.infrastructure.query.dto.BookmarkDto;
import java.util.List;

public interface BookmarkQuery {

    List<BookmarkDto> getBookmarks();
}
