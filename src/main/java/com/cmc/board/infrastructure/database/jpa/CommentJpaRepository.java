package com.cmc.board.infrastructure.database.jpa;

import com.cmc.board.domain.comment.CommentStatus;
import com.cmc.board.infrastructure.database.jpa.entity.CommentEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findByIdAndStatus(Long id, CommentStatus status);
}
