package com.cmc.board.application.port.in;

import com.cmc.board.application.port.in.command.CreatePostCommand;

public interface CreatePostUseCase {

    Long create(CreatePostCommand command);
}
