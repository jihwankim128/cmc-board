package com.cmc.board.application.port.in;

import com.cmc.board.application.port.in.command.WriteCommentCommand;

public interface WriteCommentUseCase {
    Long create(WriteCommentCommand command);
}
