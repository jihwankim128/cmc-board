package com.cmc.user.application.query;

import com.cmc.user.domain.exception.UserNotFoundException;
import com.cmc.user.infrastructure.database.jpa.UserJpaRepository;
import com.cmc.user.infrastructure.database.jpa.entity.UserEntity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryImpl implements UserQuery {

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDto getUser(Long userId) {
        return userJpaRepository.findById(userId)
                .map(user -> new UserDto(user.getId(), user.getNickname()))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Map<Long, UserDto> getUsers(List<Long> authorIds) {
        return userJpaRepository.findAllById(authorIds)
                .stream()
                .collect(Collectors.toMap(UserEntity::getId, user -> new UserDto(user.getId(), user.getNickname())));
    }
}
