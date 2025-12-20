package com.cmc.board.infrastructure.database.jpa;

import com.cmc.board.application.port.out.ValidatePostPort;
import com.cmc.board.infrastructure.database.jpa.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long>, ValidatePostPort {
}
