package com.cmc.board.infrastructure.database.query;

import static com.cmc.board.infrastructure.database.jpa.entity.QPostEntity.postEntity;

import com.cmc.board.domain.exception.PostNotFoundException;
import com.cmc.board.domain.post.PostStatus;
import com.cmc.board.infrastructure.database.jpa.PostJpaRepository;
import com.cmc.board.infrastructure.database.jpa.entity.PostEntity;
import com.cmc.board.presentation.query.CategoryQuery;
import com.cmc.board.presentation.query.PostQuery;
import com.cmc.board.presentation.query.dto.CategoryDto;
import com.cmc.board.presentation.query.dto.PostDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryImpl implements PostQuery {

    private final CategoryQuery categoryQuery;
    private final PostJpaRepository postJpaRepository;

    @Override
    public List<PostDto> getPosts(Long categoryId) {
        List<PostEntity> posts = findFilteredPosts(categoryId);
        List<Long> categoryIds = posts.stream().map(PostEntity::getCategoryId).toList();
        Map<Long, CategoryDto> categories = categoryQuery.getCategories(categoryIds);

        return posts.stream()
                .map(post -> {
                    CategoryDto category = categories.get(post.getCategoryId());
                    return PostDto.of(post, category, null);
                })
                .toList();
    }

    private List<PostEntity> findFilteredPosts(Long categoryId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(postEntity.status.eq(PostStatus.PUBLISHED));
        if (categoryId != null) {
            builder.and(postEntity.categoryId.eq(categoryId));
        }

        Predicate finalPredicate = builder.getValue();
        if (finalPredicate == null) {
            finalPredicate = Expressions.asBoolean(true).isTrue();
        }
        Iterable<PostEntity> results = postJpaRepository.findAll(finalPredicate);
        return StreamSupport.stream(results.spliterator(), false).toList();
    }

    @Override
    public PostDto getPost(Long postId, Long userId) {
        PostEntity post = postJpaRepository.findByIdAndStatus(postId, PostStatus.PUBLISHED)
                .orElseThrow(PostNotFoundException::new);
        CategoryDto category = categoryQuery.getCategory(post.getCategoryId());
        return PostDto.of(post, category, userId);
    }
}
