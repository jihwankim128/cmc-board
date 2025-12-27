package com.cmc.board.presentation.api;

import static com.cmc.board.presentation.api.status.PostSuccessStatus.CREATE_POST_SUCCESS;
import static com.cmc.board.presentation.api.status.PostSuccessStatus.DELETE_POST_SUCCESS;
import static com.cmc.board.presentation.api.status.PostSuccessStatus.GET_POSTS_SUCCESS;
import static com.cmc.board.presentation.api.status.PostSuccessStatus.UPDATE_POST_SUCCESS;

import com.cmc.board.application.port.in.CreatePostUseCase;
import com.cmc.board.application.port.in.DeletePostUseCase;
import com.cmc.board.application.port.in.UpdatePostUseCase;
import com.cmc.board.presentation.api.docs.PostApiControllerDocs;
import com.cmc.board.presentation.api.dto.post.CreatePostDto;
import com.cmc.board.presentation.api.dto.post.UpdatePostDto;
import com.cmc.board.presentation.query.PostQuery;
import com.cmc.board.presentation.query.dto.PostDto;
import com.cmc.global.auth.annotation.UserPrincipal;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.web.message.MessageSourceHelper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController implements PostApiControllerDocs {

    private final CreatePostUseCase createUseCase;
    private final UpdatePostUseCase updateUseCase;
    private final DeletePostUseCase deleteUseCase;
    private final PostQuery postQuery;
    private final MessageSourceHelper messageSourceHelper;

    @PostMapping
    public CommonResponse<Long> create(@RequestBody @Valid CreatePostDto dto, @UserPrincipal Long authorId) {
        Long result = createUseCase.create(dto.toCommand(authorId));
        String message = messageSourceHelper.extractMessage(CREATE_POST_SUCCESS);
        return CommonResponse.ok(result, CREATE_POST_SUCCESS, message);
    }

    @PutMapping("/{postId}")
    public CommonResponse<Void> update(@PathVariable Long postId,
                                       @RequestBody @Valid UpdatePostDto dto,
                                       @UserPrincipal Long authorId) {
        updateUseCase.update(dto.toCommand(postId, authorId));
        String message = messageSourceHelper.extractMessage(UPDATE_POST_SUCCESS);
        return CommonResponse.noContent(UPDATE_POST_SUCCESS, message);
    }

    @DeleteMapping("/{postId}")
    public CommonResponse<Void> delete(@PathVariable Long postId, @UserPrincipal Long authorId) {
        deleteUseCase.delete(postId, authorId);
        String message = messageSourceHelper.extractMessage(DELETE_POST_SUCCESS);
        return CommonResponse.noContent(DELETE_POST_SUCCESS, message);
    }

    @GetMapping
    public CommonResponse<List<PostDto>> getPosts() {
        // 임시 확인용
        String message = messageSourceHelper.extractMessage(GET_POSTS_SUCCESS);
        return CommonResponse.ok(postQuery.getPosts(), GET_POSTS_SUCCESS, message);
    }
}
