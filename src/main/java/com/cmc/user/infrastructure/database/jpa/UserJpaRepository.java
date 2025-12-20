package com.cmc.user.infrastructure.database.jpa;

import com.cmc.user.infrastructure.database.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
