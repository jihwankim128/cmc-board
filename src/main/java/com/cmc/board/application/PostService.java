package com.cmc.board.application;

import com.cmc.board.application.port.in.CreatePostUseCase;
import com.cmc.board.application.port.in.DeletePostUseCase;
import com.cmc.board.application.port.in.UpdatePostUseCase;
import com.cmc.board.application.port.in.command.CreatePostCommand;
import com.cmc.board.application.port.in.command.UpdatePostCommand;
import com.cmc.board.application.port.out.ValidateCategoryPort;
import com.cmc.board.domain.constants.PostExceptionStatus;
import com.cmc.board.domain.post.Post;
import com.cmc.board.domain.post.PostRepository;
import com.cmc.board.domain.post.vo.PostContent;
import com.cmc.board.domain.post.vo.PostTitle;
import com.cmc.global.common.exception.client.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService implements CreatePostUseCase, UpdatePostUseCase, DeletePostUseCase {

    private final ValidateCategoryPort validateCategoryPort;
    private final PostRepository postRepository;

    @Override
    public Long create(CreatePostCommand command) {
        validateExistCategory(command.categoryId());
        Post post = command.toPost();
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Override
    public void update(UpdatePostCommand command) {
        validateExistCategory(command.categoryId());
        postRepository.findById(command.postId())
                .ifPresentOrElse(post -> update(command, post), () -> {
                    throw new NotFoundException(PostExceptionStatus.NOT_FOUND_POST);
                });
    }

    @Override
    public void delete(Long userId, Long postId) {
        postRepository.findById(postId)
                .ifPresentOrElse(post -> {
                    post.delete(userId);
                    postRepository.save(post);
                }, () -> {
                    throw new NotFoundException(PostExceptionStatus.NOT_FOUND_POST);
                });
    }

    private void update(UpdatePostCommand command, Post post) {
        post.update(
                command.authorId(),
                command.categoryId(),
                new PostTitle(command.title()),
                new PostContent(command.content())
        );
        postRepository.save(post);
    }

    private void validateExistCategory(Long categoryId) {
        if (!validateCategoryPort.existsById(categoryId)) {
            throw new NotFoundException(PostExceptionStatus.NOT_FOUND_CATEGORY);
        }
    }
}
