package com.cmc.board.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cmc.board.application.port.in.command.CreatePostCommand;
import com.cmc.board.application.port.in.command.UpdatePostCommand;
import com.cmc.board.application.port.out.ValidateCategoryPort;
import com.cmc.board.domain.constants.CategoryExceptionStatus;
import com.cmc.board.domain.constants.PostExceptionStatus;
import com.cmc.board.domain.post.Post;
import com.cmc.board.domain.post.PostRepository;
import com.cmc.board.domain.post.vo.PostContent;
import com.cmc.board.domain.post.vo.PostTitle;
import com.cmc.global.common.exception.client.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private ValidateCategoryPort validateCategoryPort;

    @InjectMocks
    private PostService postService;

    private Post mockPost;
    private CreatePostCommand createCommand = new CreatePostCommand(1L, 1L, "새로운 게시글", "새로운 내용");
    private UpdatePostCommand updateCommand = new UpdatePostCommand(1L, 1L, 1L, "새로운 제목", "새로운 내용");

    @BeforeEach
    void setUp() {
        mockPost = mock(Post.class);
    }

    @Test
    void 게시글_생성시_없는_카테고리_정보라면_예외가_발생한다() {
        // given
        when(validateCategoryPort.existsById(anyLong())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> postService.create(createCommand))
                .isInstanceOf(NotFoundException.class)
                .hasFieldOrPropertyWithValue("status", CategoryExceptionStatus.CATEGORY_NOT_FOUND);
    }

    @Test
    void 게시글_생성_커맨드가_주어지면_게시글을_생성하고_저장한다() {
        // given
        when(validateCategoryPort.existsById(anyLong())).thenReturn(true);
        Post mockPost = mock(Post.class);
        when(mockPost.getId()).thenReturn(1L);
        when(postRepository.save(any(Post.class))).thenReturn(mockPost);

        // when
        Long result = postService.create(createCommand);

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void 게시글_수정시_없는_카테고리_정보라면_예외가_발생한다() {
        // given
        when(validateCategoryPort.existsById(anyLong())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> postService.update(updateCommand))
                .isInstanceOf(NotFoundException.class)
                .hasFieldOrPropertyWithValue("status", CategoryExceptionStatus.CATEGORY_NOT_FOUND);
    }

    @Test
    void 게시글_수정시_없는_게시글이라면_예외가_발생한다() {
        // given
        when(validateCategoryPort.existsById(anyLong())).thenReturn(true);
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postService.update(updateCommand))
                .isInstanceOf(NotFoundException.class)
                .hasFieldOrPropertyWithValue("status", PostExceptionStatus.POST_NOT_FOUND);
    }

    @Test
    void 게시글_수정시_게시글_정보를_업데이트하고_저장한다() {
        // given
        when(validateCategoryPort.existsById(anyLong())).thenReturn(true);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(mockPost));

        // when & then
        postService.update(updateCommand);

        // then
        verify(mockPost, times(1)).update(anyLong(), anyLong(), any(PostTitle.class), any(PostContent.class));
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void 게시글_삭제시_없는_게시글이라면_예외가_발생한다() {
        // given
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postService.delete(1L, 1L))
                .isInstanceOf(NotFoundException.class)
                .hasFieldOrPropertyWithValue("status", PostExceptionStatus.POST_NOT_FOUND);
    }

    @Test
    void 게시글_삭제에_성공하면_삭제_로직처리_후_삭제된_정보를_저장한다() {
        // given
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(mockPost));

        // when & then
        postService.delete(1L, 1L);

        // then
        verify(mockPost, times(1)).delete(anyLong());
        verify(postRepository, times(1)).save(any(Post.class));
    }
}
