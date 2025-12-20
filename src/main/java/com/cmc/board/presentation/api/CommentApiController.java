package com.cmc.board.presentation.api;

import static com.cmc.board.presentation.api.status.CommentSuccessStatus.CREATE_COMMENT_SUCCESS;

import com.cmc.board.application.port.in.WriteCommentUseCase;
import com.cmc.board.presentation.api.docs.CommentApiControllerDocs;
import com.cmc.board.presentation.api.dto.comment.WriteCommentDto;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.web.message.MessageSourceHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentApiController implements CommentApiControllerDocs {

    private final WriteCommentUseCase writeCommentUseCase;
    private final MessageSourceHelper messageSourceHelper;

    @PostMapping
    public CommonResponse<Long> create(@RequestBody @Valid WriteCommentDto dto) {
        Long authorId = 1L; // TODO: 인가 추가
        Long result = writeCommentUseCase.create(dto.toCommand(authorId));
        String message = messageSourceHelper.extractMessage(CREATE_COMMENT_SUCCESS);
        return CommonResponse.ok(result, CREATE_COMMENT_SUCCESS, message);
    }
}
