package com.cmc.board.presentation.api;

import static com.cmc.board.presentation.api.status.PostSuccessStatus.CREATE_POST_SUCCESS;

import com.cmc.board.application.port.in.CreatePostUseCase;
import com.cmc.board.presentation.api.docs.PostApiControllerDocs;
import com.cmc.board.presentation.api.dto.post.CreatePostDto;
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
@RequestMapping("/posts")
public class PostApiController implements PostApiControllerDocs {

    private final CreatePostUseCase createUseCase;
    private final MessageSourceHelper messageSourceHelper;

    @PostMapping
    public CommonResponse<Long> create(@RequestBody @Valid CreatePostDto dto) {
        Long authorId = 1L; // TODO: 인가 추가
        Long result = createUseCase.create(dto.toCommand(authorId));
        String message = messageSourceHelper.extractMessage(CREATE_POST_SUCCESS);
        return CommonResponse.ok(result, CREATE_POST_SUCCESS, message);
    }
}
