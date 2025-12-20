package com.cmc.board.application.port.in;

import com.cmc.board.application.port.in.command.ReplyCommentCommand;

public interface WriteReplyUseCase {
    Long reply(ReplyCommentCommand command);
}
