package com.commerce.global.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name = "activated", nullable = false)
    protected boolean activated = true;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    protected LocalDateTime createdAt = LocalDateTime.now();

    @CreatedDate
    @Column(name = "updated_at", insertable = false)
    protected LocalDateTime updatedAt;
}
