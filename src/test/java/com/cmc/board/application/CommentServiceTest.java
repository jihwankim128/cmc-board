package com.cmc.board.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cmc.board.application.port.in.command.WriteCommentCommand;
import com.cmc.board.application.port.out.ValidatePostPort;
import com.cmc.board.domain.comment.Comment;
import com.cmc.board.domain.comment.CommentRepository;
import com.cmc.board.domain.constants.PostExceptionStatus;
import com.cmc.board.domain.exception.PostNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ValidatePostPort validatePostPort;

    @InjectMocks
    private CommentService commentService;

    private WriteCommentCommand createCommand = new WriteCommentCommand(1L, 1L, "댓글입니다");

    private Comment mockComment;

    @BeforeEach
    void setUp() {
        mockComment = mock(Comment.class);
    }

    @Test
    void 댓글_생성시_게시글이_존재하지않으면_예외가_발생한다() {
        // given
        when(validatePostPort.existsById(anyLong())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> commentService.create(createCommand))
                .isInstanceOf(PostNotFoundException.class)
                .hasFieldOrPropertyWithValue("status", PostExceptionStatus.NOT_FOUND_POST);
    }

    @Test
    void 댓글_생성_커맨드가_발생하면_생성된_댓글을_저장한다() {
        // given
        when(validatePostPort.existsById(anyLong())).thenReturn(true);
        when(mockComment.getId()).thenReturn(1L);
        when(commentRepository.save(any(Comment.class))).thenReturn(mockComment);

        // when
        Long result = commentService.create(createCommand);

        // then
        assertThat(result).isEqualTo(1L);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }
}