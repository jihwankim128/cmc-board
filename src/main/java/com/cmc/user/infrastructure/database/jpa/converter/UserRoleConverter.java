package com.cmc.user.infrastructure.database.jpa.converter;

import com.cmc.user.domain.vo.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole status) {
        return status.name();
    }

    @Override
    public UserRole convertToEntityAttribute(String data) {
        return UserRole.valueOf(data);
    }
}
