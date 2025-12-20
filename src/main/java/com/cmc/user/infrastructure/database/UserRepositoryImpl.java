package com.cmc.user.infrastructure.database;

import com.cmc.user.domain.User;
import com.cmc.user.domain.UserRepository;
import com.cmc.user.infrastructure.database.jpa.UserJpaRepository;
import com.cmc.user.infrastructure.database.jpa.entity.UserEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public User save(User user) {
        UserEntity saved = jpaRepository.save(UserEntity.from(user));
        return saved.toDomain();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
