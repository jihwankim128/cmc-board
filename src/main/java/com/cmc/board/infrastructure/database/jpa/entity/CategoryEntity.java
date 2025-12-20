package com.cmc.board.infrastructure.database.jpa.entity;

import com.cmc.board.domain.Category;
import com.cmc.board.domain.vo.info.CategoryName;
import com.cmc.global.database.TimeBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryEntity extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public static CategoryEntity from(Category category) {
        return new CategoryEntity(category.getId(), category.getName());
    }

    public Category toDomain() {
        return Category.of(this.id, new CategoryName(this.name));
    }
}
