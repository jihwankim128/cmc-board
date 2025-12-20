package com.cmc.board.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cmc.board.application.port.in.command.UpdateCategoryCommand;
import com.cmc.board.domain.category.Category;
import com.cmc.board.domain.category.CategoryRepository;
import com.cmc.board.domain.category.vo.CategoryName;
import com.cmc.board.domain.constants.CategoryExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;
import com.cmc.global.common.exception.client.NotFoundException;
import java.util.Optional;
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
        when(mockCategory.getId()).thenReturn(1L);
        when(categoryRepository.existsByName(anyString())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory);
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
                .hasFieldOrPropertyWithValue("status", CategoryExceptionStatus.CATEGORY_NAME_DUPLICATED);
    }

    @Test
    void 존재하지_않는_카테고리를_수정할_경우_예외가_발생한다() {
        // given
        UpdateCategoryCommand command = new UpdateCategoryCommand(1L, "카테고리 수정");
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> categoryService.update(command))
                .isInstanceOf(NotFoundException.class)
                .hasFieldOrPropertyWithValue("status", CategoryExceptionStatus.CATEGORY_NOT_FOUND);
    }

    @Test
    void 카테고리_수정시_중복되는_카테고리_이름이_있다면_예외가_발생한다() {
        // given
        UpdateCategoryCommand command = new UpdateCategoryCommand(1L, "카테고리 수정");
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(mockCategory));
        when(categoryRepository.existsByNameExcludeId(anyString(), anyLong())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> categoryService.update(command))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", CategoryExceptionStatus.CATEGORY_NAME_DUPLICATED);
    }

    @Test
    void 카테고리_수정이_성공하면_수정된_카테고리_정보를_저장한다() {
        // given
        UpdateCategoryCommand command = new UpdateCategoryCommand(1L, "카테고리 수정");
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(mockCategory));
        when(categoryRepository.existsByNameExcludeId(anyString(), anyLong())).thenReturn(false);

        // when
        categoryService.update(command);

        // then
        verify(mockCategory, times(1)).update(any(CategoryName.class));
        verify(categoryRepository, times(1)).save(any(Category.class));
    }
}