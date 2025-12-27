package com.cmc.board.infrastructure.database.query;

import com.cmc.board.domain.exception.PostNotFoundException;
import com.cmc.board.domain.post.PostStatus;
import com.cmc.board.infrastructure.database.jpa.PostJpaRepository;
import com.cmc.board.infrastructure.database.jpa.entity.PostEntity;
import com.cmc.board.presentation.query.CategoryQuery;
import com.cmc.board.presentation.query.PostQuery;
import com.cmc.board.presentation.query.dto.CategoryDto;
import com.cmc.board.presentation.query.dto.PostDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryImpl implements PostQuery {

    private final CategoryQuery categoryQuery;
    private final PostJpaRepository postJpaRepository;

    @Override
    public List<PostDto> getPosts() {
        return postJpaRepository.findAll()
                .stream()
                .map(post -> new PostDto(
                        post.getId(),
                        post.getAuthorId(),
                        null,
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt(),
                        null,
                        null
                ))
                .toList();
    }

    @Override
    public PostDto getPost(Long postId, Long userId) {
        PostEntity post = postJpaRepository.findByIdAndStatus(postId, PostStatus.PUBLISHED)
                .orElseThrow(PostNotFoundException::new);
        CategoryDto category = categoryQuery.getCategory(post.getCategoryId());
        return PostDto.of(post, category, userId);
    }
}
