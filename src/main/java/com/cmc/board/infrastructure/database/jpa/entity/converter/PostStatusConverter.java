package com.cmc.board.infrastructure.database.jpa.entity.converter;

import com.cmc.board.domain.post.PostStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PostStatusConverter implements AttributeConverter<PostStatus, String> {

    @Override
    public String convertToDatabaseColumn(PostStatus status) {
        return status.name();
    }

    @Override
    public PostStatus convertToEntityAttribute(String data) {
        return PostStatus.valueOf(data);
    }
}
