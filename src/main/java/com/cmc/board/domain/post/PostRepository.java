package com.cmc.board.domain.post;

import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(Long postId);
}
