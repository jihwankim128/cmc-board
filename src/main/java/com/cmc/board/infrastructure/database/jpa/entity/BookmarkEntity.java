package com.cmc.board.infrastructure.database.jpa.entity;

import com.cmc.board.domain.bookmark.Bookmark;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "bookmarks")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BookmarkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public static BookmarkEntity from(Bookmark bookmark) {
        return BookmarkEntity.builder()
                .id(bookmark.getId())
                .postId(bookmark.getPostId())
                .userId(bookmark.getUserId())
                .build();
    }

    public Bookmark toDomain() {
        return Bookmark.of(id, postId, userId);
    }
}
