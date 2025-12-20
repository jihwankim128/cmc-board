package com.cmc.board.application;

import com.cmc.board.application.port.in.CreatePostUseCase;
import com.cmc.board.application.port.in.command.CreatePostCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService implements CreatePostUseCase {
    @Override
    public Long create(CreatePostCommand command) {
        return 0L;
    }
}
