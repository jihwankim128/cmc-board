package com.cmc.board.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cmc.board.domain.Category;
import com.cmc.board.domain.CategoryRepository;
import com.cmc.board.domain.constants.BoardExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    private Category mockCategory;

    @BeforeEach
    void setUp() {
        mockCategory = mock(Category.class);
    }

    @Test
    void 카테고리_이름_정보로_생성된_카테고리를_저장하고_식별자를_반환된다() {
        // given
        카테고리_생성_스텁();
        String categoryName = "새로운 카테고리";

        // when
        Long categoryId = categoryService.create(categoryName);

        // then
        verify(categoryRepository, times(1)).save(any(Category.class));
        assertThat(categoryId).isEqualTo(1L);
    }

    @Test
    void 카테고리_이름_정보가_중복되면_예외를_던진다() {
        when(categoryRepository.existsByName(anyString())).thenReturn(true);
        String duplicateCategoryName = "중복 카테고리";

        // when & then
        assertThatThrownBy(() -> categoryService.create(duplicateCategoryName))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", BoardExceptionStatus.CATEGORY_NAME_DUPLICATED);
    }

    private void 카테고리_생성_스텁() {
        when(mockCategory.getId()).thenReturn(1L);
        when(categoryRepository.existsByName(anyString())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory);
    }
}