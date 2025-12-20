package com.cmc.board.presentation.query;

import com.cmc.board.presentation.query.dto.BookmarkDto;
import java.util.List;

public interface BookmarkQuery {

    List<BookmarkDto> getBookmarks();
}
