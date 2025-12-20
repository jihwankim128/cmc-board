package com.cmc.board.application.port.in;

import com.cmc.board.application.port.in.command.UpdateCommentCommand;

public interface UpdateCommentUseCase {
    void update(UpdateCommentCommand command);
}
