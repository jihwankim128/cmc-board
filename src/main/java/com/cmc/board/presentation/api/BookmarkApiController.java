package com.cmc.board.presentation.api;

import static com.cmc.board.presentation.api.status.BookmarkSuccessStatus.CREATE_BOOKMARK_SUCCESS;
import static com.cmc.board.presentation.api.status.BookmarkSuccessStatus.GET_BOOKMARKS_SUCCESS;

import com.cmc.board.application.port.in.CreateBookmarkUseCase;
import com.cmc.board.presentation.api.docs.BookmarkApiControllerDocs;
import com.cmc.board.presentation.api.dto.bookmark.CreateBookmarkDto;
import com.cmc.board.presentation.query.BookmarkQuery;
import com.cmc.board.presentation.query.dto.BookmarkDto;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.web.message.MessageSourceHelper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkApiController implements BookmarkApiControllerDocs {

    private final CreateBookmarkUseCase createUseCase;
    private final BookmarkQuery bookmarkQuery;
    private final MessageSourceHelper messageSourceHelper;

    @PostMapping
    public CommonResponse<Void> createBookmark(@RequestBody @Valid CreateBookmarkDto dto) {
        Long userId = 1L;
        createUseCase.create(dto.postId(), userId);
        String message = messageSourceHelper.extractMessage(CREATE_BOOKMARK_SUCCESS);
        return CommonResponse.noContent(CREATE_BOOKMARK_SUCCESS, message);
    }

    @GetMapping
    public CommonResponse<List<BookmarkDto>> getBookmarks() {
        String message = messageSourceHelper.extractMessage(GET_BOOKMARKS_SUCCESS);
        return CommonResponse.ok(bookmarkQuery.getBookmarks(), GET_BOOKMARKS_SUCCESS, message);
    }
}
