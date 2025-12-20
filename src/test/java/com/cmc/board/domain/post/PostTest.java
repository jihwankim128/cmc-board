package com.cmc.board.domain.post;

import static com.cmc.board.domain.constants.PostExceptionStatus.MISMATCH_POST_AUTHOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cmc.board.domain.post.vo.PostContent;
import com.cmc.board.domain.post.vo.PostTitle;
import com.cmc.global.common.exception.client.ForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostTest {

    private final Long authorId = 1L;
    private final Long otherUserId = 2L;
    private final Long categoryId = 1L;
    private final PostTitle title = new PostTitle("테스트 제목");
    private final PostContent content = new PostContent("테스트 내용입니다.");
    private Post post;

    @BeforeEach
    void setUp() {
        post = Post.create(authorId, categoryId, title, content);
    }

    @Test
    void 게시글을_생성시_상태는_PUBLISHED이다() {
        // then
        assertThat(post.getStatus()).isEqualTo(PostStatus.PUBLISHED);
    }

    @Test
    void 작성자는_게시글을_수정할_수_있다() {
        // given
        PostTitle newTitle = new PostTitle("수정된 제목");
        PostContent newContent = new PostContent("수정된 내용입니다.");
        Long newCategoryId = 2L;

        // when
        post.update(authorId, newCategoryId, newTitle, newContent);

        // then
        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(newContent);
        assertThat(post.getCategoryId()).isEqualTo(newCategoryId);
    }

    @Test
    void 작성자가_아닌_사람이_게시글을_수정하면_에외가_발생한다() {
        // given
        PostContent newContent = new PostContent("수정된 내용입니다.");

        // when & then
        assertThatThrownBy(() -> post.update(otherUserId, categoryId, title, newContent))
                .isInstanceOf(ForbiddenException.class)
                .hasFieldOrPropertyWithValue("status", MISMATCH_POST_AUTHOR);
    }

    @Test
    void 작성자는_게시글을_삭제할_수_있다() {
        // when
        post.delete(authorId);

        // then
        assertThat(post.getStatus()).isEqualTo(PostStatus.DELETED);
    }

    @Test
    void 작성자가_아닌_사람이_게시글을_삭제하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> post.delete(otherUserId))
                .isInstanceOf(ForbiddenException.class)
                .hasFieldOrPropertyWithValue("status", MISMATCH_POST_AUTHOR);
    }
}