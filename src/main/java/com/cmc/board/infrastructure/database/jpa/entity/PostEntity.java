package com.cmc.board.infrastructure.database.jpa.entity;

import com.cmc.board.domain.post.Post;
import com.cmc.board.domain.post.PostStatus;
import com.cmc.board.domain.post.vo.PostContent;
import com.cmc.board.domain.post.vo.PostTitle;
import com.cmc.board.infrastructure.database.jpa.entity.converter.PostStatusConverter;
import com.cmc.global.database.TimeBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false, length = 20)
    @Convert(converter = PostStatusConverter.class)
    private PostStatus status;

    public static PostEntity from(Post post) {
        return new PostEntity(
                post.getId(),
                post.getAuthorId(),
                post.getCategoryId(),
                post.getTitle().value(),
                post.getContent().value(),
                post.getStatus()
        );
    }

    public Post toDomain() {
        return Post.of(id, authorId, categoryId, new PostTitle(title), new PostContent(content), status);
    }
}
