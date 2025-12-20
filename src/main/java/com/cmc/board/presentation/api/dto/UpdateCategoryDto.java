package com.cmc.board.presentation.api.dto;

import com.cmc.board.application.port.in.command.UpdateCategoryCommand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateCategoryDto(
        @NotEmpty(message = "{category.name.empty}")
        @Size(max = 20, message = "{category.name.tooLong}")
        String name
) {

    public UpdateCategoryCommand toCommand(Long id) {
        return new UpdateCategoryCommand(id, name);
    }
}
