package com.cmc.board.presentation.api;

import static com.cmc.board.presentation.api.status.CommentSuccessStatus.CREATE_COMMENT_SUCCESS;
import static com.cmc.board.presentation.api.status.CommentSuccessStatus.CREATE_REPLY_SUCCESS;
import static com.cmc.board.presentation.api.status.CommentSuccessStatus.DELETE_COMMENT_SUCCESS;
import static com.cmc.board.presentation.api.status.CommentSuccessStatus.UPDATE_COMMENT_SUCCESS;

import com.cmc.board.application.port.in.DeleteCommentUseCase;
import com.cmc.board.application.port.in.UpdateCommentUseCase;
import com.cmc.board.application.port.in.WriteCommentUseCase;
import com.cmc.board.application.port.in.WriteReplyUseCase;
import com.cmc.board.infrastructure.query.CommentQuery;
import com.cmc.board.infrastructure.query.dto.CommentDto;
import com.cmc.board.presentation.api.docs.CommentApiControllerDocs;
import com.cmc.board.presentation.api.dto.comment.ReplyCommentDto;
import com.cmc.board.presentation.api.dto.comment.UpdateCommentDto;
import com.cmc.board.presentation.api.dto.comment.WriteCommentDto;
import com.cmc.global.auth.annotation.AuthRole;
import com.cmc.global.auth.annotation.PreAuth;
import com.cmc.global.auth.annotation.UserPrincipal;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.web.message.MessageSourceHelper;
import jakarta.validation.Valid;
import java.util.List;
import javax.annotation.Nullable;
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
@RequestMapping("/api/comments")
public class CommentApiController implements CommentApiControllerDocs {

    private final WriteCommentUseCase writeCommentUseCase;
    private final WriteReplyUseCase writeReplyUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final CommentQuery commentQuery;
    private final MessageSourceHelper messageSourceHelper;

    @PostMapping
    @PreAuth
    public CommonResponse<Long> create(@RequestBody @Valid WriteCommentDto dto, @UserPrincipal Long authorId) {
        Long result = writeCommentUseCase.create(dto.toCommand(authorId));
        String message = messageSourceHelper.extractMessage(CREATE_COMMENT_SUCCESS);
        return CommonResponse.ok(result, CREATE_COMMENT_SUCCESS, message);
    }

    @PostMapping("{parentId}/reply")
    @PreAuth
    public CommonResponse<Long> reply(@PathVariable Long parentId,
                                      @RequestBody @Valid ReplyCommentDto dto,
                                      @UserPrincipal Long authorId) {
        Long result = writeReplyUseCase.reply(dto.toCommand(authorId, parentId));
        String message = messageSourceHelper.extractMessage(CREATE_REPLY_SUCCESS);
        return CommonResponse.ok(result, CREATE_REPLY_SUCCESS, message);
    }

    @PutMapping("/{commentId}")
    @PreAuth
    public CommonResponse<Void> update(@PathVariable Long commentId,
                                       @RequestBody @Valid UpdateCommentDto dto,
                                       @UserPrincipal Long authorId) {
        updateCommentUseCase.update(dto.toCommand(commentId, authorId));
        String message = messageSourceHelper.extractMessage(UPDATE_COMMENT_SUCCESS);
        return CommonResponse.noContent(UPDATE_COMMENT_SUCCESS, message);
    }

    @DeleteMapping("/{commentId}")
    @PreAuth
    public CommonResponse<Void> delete(@PathVariable Long commentId, @UserPrincipal Long authorId) {
        deleteCommentUseCase.delete(authorId, commentId);
        String message = messageSourceHelper.extractMessage(DELETE_COMMENT_SUCCESS);
        return CommonResponse.noContent(DELETE_COMMENT_SUCCESS, message);
    }

    @GetMapping("/posts/{postId}")
    @PreAuth(value = AuthRole.ANONYMOUS)
    public CommonResponse<List<CommentDto>> getComments(@PathVariable Long postId,
                                                        @UserPrincipal @Nullable Long viewerId) {
        System.out.printf("userID = %d%n", viewerId);
        List<CommentDto> result = commentQuery.getComments(postId, viewerId);
        String message = messageSourceHelper.extractMessage(UPDATE_COMMENT_SUCCESS);
        return CommonResponse.ok(result, UPDATE_COMMENT_SUCCESS, message);
    }
}
