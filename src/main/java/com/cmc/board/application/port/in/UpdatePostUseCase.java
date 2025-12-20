package com.cmc.board.application.port.in;

import com.cmc.board.application.port.in.command.UpdatePostCommand;

public interface UpdatePostUseCase {

    void update(UpdatePostCommand command);
}
