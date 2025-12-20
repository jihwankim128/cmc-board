package com.cmc.board.application;

import static com.cmc.board.domain.comment.CommentStatus.PUBLISHED;
import static com.cmc.board.domain.comment.vo.CommentDepth.BASE_DEPTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cmc.board.application.port.in.command.ReplyCommentCommand;
import com.cmc.board.application.port.in.command.UpdateCommentCommand;
import com.cmc.board.application.port.in.command.WriteCommentCommand;
import com.cmc.board.application.port.out.ValidatePostPort;
import com.cmc.board.domain.comment.Comment;
import com.cmc.board.domain.comment.CommentRepository;
import com.cmc.board.domain.comment.vo.CommentContent;
import com.cmc.board.domain.constants.CommentExceptionStatus;
import com.cmc.board.domain.constants.PostExceptionStatus;
import com.cmc.board.domain.exception.CommentNotFoundException;
import com.cmc.board.domain.exception.PostNotFoundException;
import java.util.Optional;
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
    private ReplyCommentCommand replyCommand = new ReplyCommentCommand(1L, 1L, "대댓글입니다");
    private UpdateCommentCommand updateCommand = new UpdateCommentCommand(1L, 1L, "수정 댓글입니다");

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
                .hasFieldOrPropertyWithValue("status", PostExceptionStatus.POST_NOT_FOUND);
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

    @Test
    void 대댓글_생성시_부모_댓글이_존재하지않으면_예외가_발생한다() {
        // given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.reply(replyCommand))
                .isInstanceOf(CommentNotFoundException.class)
                .hasFieldOrPropertyWithValue("status", CommentExceptionStatus.COMMENT_NOT_FOUND);
    }

    @Test
    void 대댓글_생성_커맨드가_발생하면_대댓글을_생성하고_저장한다() {
        // given
        Comment parent = Comment.of(1L, 1L, null, 1L, BASE_DEPTH, new CommentContent("부모 댓글입니다."), PUBLISHED);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(parent));
        when(mockComment.getId()).thenReturn(2L);
        when(commentRepository.save(any(Comment.class))).thenReturn(mockComment);

        // when
        Long result = commentService.reply(replyCommand);

        // then
        assertThat(result).isEqualTo(2L);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void 댓글_수정시_댓글이_존재하지않으면_예외가_발생한다() {
        // given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.update(updateCommand))
                .isInstanceOf(CommentNotFoundException.class)
                .hasFieldOrPropertyWithValue("status", CommentExceptionStatus.COMMENT_NOT_FOUND);
    }

    @Test
    void 댓글_수정_커맨드가_발생하면_댓글을_수정하고_저장한다() {
        // given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(mockComment));

        // when
        commentService.update(updateCommand);

        // then
        verify(mockComment, times(1)).update(anyLong(), any(CommentContent.class));
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void 댓글_삭제시_댓글이_존재하지않으면_예외가_발생한다() {
        // given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.delete(1L, 1L))
                .isInstanceOf(CommentNotFoundException.class)
                .hasFieldOrPropertyWithValue("status", CommentExceptionStatus.COMMENT_NOT_FOUND);
    }

    @Test
    void 댓글_삭제_커맨드가_발생하면_댓글을_삭제하고_저장한다() {
        // given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(mockComment));

        // when
        commentService.delete(1L, 1L);

        // then
        verify(mockComment, times(1)).delete(anyLong());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }
}