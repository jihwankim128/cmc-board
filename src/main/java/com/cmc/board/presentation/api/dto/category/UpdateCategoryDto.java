package com.cmc.board.presentation.api.dto.category;

import com.cmc.board.application.port.in.command.UpdateCategoryCommand;
import jakarta.validation.constraints.NotEmpty;

public record UpdateCategoryDto(
        @NotEmpty(message = "{category.name.empty}")
        String name
) {

    public UpdateCategoryCommand toCommand(Long id) {
        return new UpdateCategoryCommand(id, name);
    }
}
