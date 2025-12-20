package com.cmc.board.infrastructure.database.query;

import com.cmc.board.infrastructure.database.jpa.PostJpaRepository;
import com.cmc.board.presentation.query.PostQuery;
import com.cmc.board.presentation.query.dto.PostDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryImpl implements PostQuery {

    private final PostJpaRepository postJpaRepository;

    @Override
    public List<PostDto> getPosts() {
        return postJpaRepository.findAll()
                .stream()
                .map(post -> new PostDto(
                        post.getId(),
                        post.getAuthorId(),
                        post.getCategoryId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getStatus()
                ))
                .toList();
    }
}
