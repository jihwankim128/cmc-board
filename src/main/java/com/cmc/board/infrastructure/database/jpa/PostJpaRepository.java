package com.cmc.board.infrastructure.database.jpa;

import com.cmc.board.application.port.out.ValidatePostPort;
import com.cmc.board.domain.post.PostStatus;
import com.cmc.board.infrastructure.database.jpa.entity.PostEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostJpaRepository extends
        JpaRepository<PostEntity, Long>,
        QuerydslPredicateExecutor<PostEntity>,
        ValidatePostPort {

    Optional<PostEntity> findByIdAndStatus(Long postId, PostStatus status);

    List<PostEntity> findTop10ByStatusOrderByIdDesc(PostStatus postStatus);
}
