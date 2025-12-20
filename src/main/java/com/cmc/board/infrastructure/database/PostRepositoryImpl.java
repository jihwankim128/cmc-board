package com.cmc.board.infrastructure.database;

import com.cmc.board.domain.post.Post;
import com.cmc.board.domain.post.PostRepository;
import com.cmc.board.infrastructure.database.jpa.PostJpaRepository;
import com.cmc.board.infrastructure.database.jpa.entity.PostEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository jpaRepository;

    @Override
    public Post save(Post post) {
        PostEntity savedEntity = jpaRepository.save(PostEntity.from(post));
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return Optional.empty();
    }
}
