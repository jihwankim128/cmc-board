package com.cmc.global.database;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SoftDeleteEntity extends TimeBaseEntity {

    @Builder.Default
    @Column(nullable = false)
    private boolean deleted = false;

    private Instant deletedAt;

    public void delete() {
        this.deleted = true;
        this.deletedAt = Instant.now();
    }
}
