package com.cmc.board.infrastructure.query;

import static com.cmc.board.infrastructure.database.jpa.entity.QPostEntity.postEntity;

import com.cmc.board.domain.exception.PostNotFoundException;
import com.cmc.board.domain.post.PostStatus;
import com.cmc.board.infrastructure.database.jpa.PostJpaRepository;
import com.cmc.board.infrastructure.database.jpa.entity.PostEntity;
import com.cmc.board.infrastructure.query.dto.CategoryDto;
import com.cmc.board.infrastructure.query.dto.PostDto;
import com.cmc.user.application.query.UserDto;
import com.cmc.user.application.query.UserQuery;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryImpl implements PostQuery {

    private final CategoryQuery categoryQuery;
    private final UserQuery userQuery;
    private final PostJpaRepository postJpaRepository;

    @Override
    public List<PostDto> getPosts(Long categoryId) {
        List<PostEntity> posts = findFilteredPosts(categoryId);
        List<Long> categoryIds = posts.stream().map(PostEntity::getCategoryId).toList();
        List<Long> authorIds = posts.stream().map(PostEntity::getAuthorId).toList();
        Map<Long, CategoryDto> categories = categoryQuery.getCategories(categoryIds);
        Map<Long, UserDto> authors = userQuery.getUsers(authorIds);

        return posts.stream()
                .map(post -> {
                    CategoryDto category = categories.get(post.getCategoryId());
                    UserDto author = authors.get(post.getAuthorId());
                    return PostDto.of(post, author, category, null);
                })
                .toList();
    }

    @Override
    public PostDto getPost(Long postId, Long userId) {
        PostEntity post = postJpaRepository.findByIdAndStatus(postId, PostStatus.PUBLISHED)
                .orElseThrow(PostNotFoundException::new);
        CategoryDto category = categoryQuery.getCategory(post.getCategoryId());
        UserDto author = userQuery.getUser(post.getAuthorId());
        return PostDto.of(post, author, category, userId);
    }

    private List<PostEntity> findFilteredPosts(Long categoryId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(postEntity.status.eq(PostStatus.PUBLISHED));
        if (categoryId != null) {
            builder.and(postEntity.categoryId.eq(categoryId));
        }

        Predicate finalPredicate = builder.getValue();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        if (finalPredicate == null) {
            finalPredicate = Expressions.asBoolean(true).isTrue();
        }
        Iterable<PostEntity> results = postJpaRepository.findAll(finalPredicate, sort);
        return StreamSupport.stream(results.spliterator(), false).toList();
    }
}
