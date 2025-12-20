package com.cmc.board.application;

import com.cmc.board.application.port.in.CreatePostUseCase;
import com.cmc.board.application.port.in.command.CreatePostCommand;
import com.cmc.board.application.port.out.ValidateCategoryPort;
import com.cmc.board.domain.constants.PostExceptionStatus;
import com.cmc.board.domain.post.Post;
import com.cmc.board.domain.post.PostRepository;
import com.cmc.global.common.exception.client.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService implements CreatePostUseCase {

    private final ValidateCategoryPort validateCategoryPort;
    private final PostRepository postRepository;

    @Override
    public Long create(CreatePostCommand command) {
        validateExistCategory(command);
        Post post = command.toPost();
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    private void validateExistCategory(CreatePostCommand command) {
        if (!validateCategoryPort.existsById(command.categoryId())) {
            throw new NotFoundException(PostExceptionStatus.NOT_FOUND_CATEGORY);
        }
    }
}
