package com.cmc.board.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cmc.board.application.port.in.command.CreatePostCommand;
import com.cmc.board.application.port.out.ValidateCategoryPort;
import com.cmc.board.domain.constants.PostExceptionStatus;
import com.cmc.board.domain.post.Post;
import com.cmc.board.domain.post.PostRepository;
import com.cmc.global.common.exception.client.NotFoundException;
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

    private CreatePostCommand createCommand = new CreatePostCommand(1L, 1L, "새로운 게시글", "새로운 내용");

    @Test
    void 게시글_생성시_없는_카테고리_정보라면_예외가_발생한다() {
        // given
        when(validateCategoryPort.existsCategory(anyLong())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> postService.create(createCommand))
                .isInstanceOf(NotFoundException.class)
                .hasFieldOrPropertyWithValue("status", PostExceptionStatus.NOT_FOUND_CATEGORY);
    }

    @Test
    void 게시글_생성_커맨드가_주어지면_게시글을_생성하고_저장한다() {
        // given
        when(validateCategoryPort.existsCategory(anyLong())).thenReturn(true);
        Post mockPost = mock(Post.class);
        when(mockPost.getId()).thenReturn(1L);
        when(postRepository.save(any(Post.class))).thenReturn(mockPost);

        // when
        Long result = postService.create(createCommand);

        // then
        assertThat(result).isEqualTo(1L);
    }
}
