package com.cmc.board.infrastructure.database.jpa;

import com.cmc.board.infrastructure.database.jpa.entity.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkJpaRepository extends JpaRepository<BookmarkEntity, Long> {
}
