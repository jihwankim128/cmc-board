package com.cmc.board.application.port.in;

import com.cmc.board.application.port.in.command.UpdateCategoryCommand;

public interface UpdateCategoryUseCase {

    void update(UpdateCategoryCommand command);
}