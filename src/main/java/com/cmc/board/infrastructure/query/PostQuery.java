package com.cmc.board.infrastructure.query;

import com.cmc.board.infrastructure.query.dto.PostDto;
import java.util.List;
import java.util.Map;

public interface PostQuery {

    List<PostDto> getPosts(Long categoryId);

    PostDto getPosts(Long postId, Long userId);

    List<PostDto> getLatest();

    Map<Long, PostDto> getPosts(List<Long> postIds);
}
