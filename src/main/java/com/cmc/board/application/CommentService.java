package com.cmc.board.application;

import com.cmc.board.application.port.in.WriteCommentUseCase;
import com.cmc.board.application.port.in.WriteReplyUseCase;
import com.cmc.board.application.port.in.command.ReplyCommentCommand;
import com.cmc.board.application.port.in.command.WriteCommentCommand;
import com.cmc.board.application.port.out.ValidatePostPort;
import com.cmc.board.domain.comment.Comment;
import com.cmc.board.domain.comment.CommentRepository;
import com.cmc.board.domain.exception.CommentNotFoundException;
import com.cmc.board.domain.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService implements WriteCommentUseCase, WriteReplyUseCase {
    private final CommentRepository commentRepository;
    private final ValidatePostPort validatePostPort;

    @Override
    public Long create(WriteCommentCommand command) {
        validatePost(command.postId());
        Comment saved = commentRepository.save(command.toComment());
        return saved.getId();
    }

    @Override
    public Long reply(ReplyCommentCommand command) {
        validatePost(command.postId());
        Comment parent = commentRepository.findById(command.parentCommentId())
                .orElseThrow(CommentNotFoundException::new);
        Comment reply = command.toReply(parent);
        Comment saved = commentRepository.save(reply);
        return saved.getId();
    }

    private void validatePost(Long postId) {
        if (!validatePostPort.existsById(postId)) {
            throw new PostNotFoundException();
        }
    }
}
