package com.cmc.board.presentation.query;

import com.cmc.board.presentation.query.dto.PostDto;
import java.util.List;

public interface PostQuery {

    List<PostDto> getPosts();

    PostDto getPost(Long postId, Long userId);
}
