package com.cmc.board.application.port.in.command;

import com.cmc.board.domain.post.Post;
import com.cmc.board.domain.post.vo.PostContent;
import com.cmc.board.domain.post.vo.PostTitle;

public record CreatePostCommand(Long authorId, Long categoryId, String title, String content) {
    public Post toPost() {
        return Post.create(authorId, categoryId, new PostTitle(title), new PostContent(content));
    }
}
