package com.cmc.board.infrastructure.database.jpa.entity.converter;

import com.cmc.board.domain.comment.CommentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CommentStatusConverter implements AttributeConverter<CommentStatus, String> {

    @Override
    public String convertToDatabaseColumn(CommentStatus status) {
        return status.name();
    }

    @Override
    public CommentStatus convertToEntityAttribute(String data) {
        return CommentStatus.valueOf(data);
    }
}
