package com.cmc.board.infrastructure.query;

import com.cmc.board.infrastructure.query.dto.PostDto;
import java.util.List;

public interface PostQuery {

    List<PostDto> getPosts(Long categoryId);

    PostDto getPost(Long postId, Long userId);

    List<PostDto> getLatest();
}
