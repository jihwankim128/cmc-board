package com.cmc.board.infrastructure.database.jpa.entity;

import com.cmc.board.domain.comment.Comment;
import com.cmc.board.domain.comment.CommentStatus;
import com.cmc.board.domain.comment.vo.CommentContent;
import com.cmc.board.domain.comment.vo.CommentDepth;
import com.cmc.board.infrastructure.database.jpa.entity.converter.CommentStatusConverter;
import com.cmc.global.database.TimeBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "comments")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column
    private Long parentId;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private Integer depth;

    @Column(nullable = false)
    private String content;

    @Convert(converter = CommentStatusConverter.class)
    @Column(nullable = false, length = 20)
    private CommentStatus status;

    public static CommentEntity from(Comment comment) {
        return new CommentEntity(
                comment.getId(),
                comment.getPostId(),
                comment.getParentId(),
                comment.getAuthorId(),
                comment.getDepth().value(),
                comment.getContent().value(),
                comment.getStatus()
        );
    }

    public Comment toDomain() {
        CommentDepth depth = new CommentDepth(this.depth);
        CommentContent content = new CommentContent(this.content);
        return Comment.of(id, postId, parentId, authorId, depth, content, status);
    }
}
