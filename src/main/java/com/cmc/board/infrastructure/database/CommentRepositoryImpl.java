package com.cmc.board.infrastructure.database;

import com.cmc.board.domain.comment.Comment;
import com.cmc.board.domain.comment.CommentRepository;
import com.cmc.board.domain.comment.CommentStatus;
import com.cmc.board.infrastructure.database.jpa.CommentJpaRepository;
import com.cmc.board.infrastructure.database.jpa.entity.CommentEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository jpaRepository;

    @Override
    public Comment save(Comment comment) {
        CommentEntity entity = jpaRepository.save(CommentEntity.from(comment));
        return entity.toDomain();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return jpaRepository.findByIdAndStatus(id, CommentStatus.PUBLISHED)
                .map(CommentEntity::toDomain);
    }
}
