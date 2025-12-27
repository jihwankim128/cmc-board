package com.cmc.global.database;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class TimeBaseEntity {

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;
    @Column
    @LastModifiedDate
    private Instant modifiedAt;

    public boolean isModified() {
        return !createdAt.equals(modifiedAt);
    }
}
