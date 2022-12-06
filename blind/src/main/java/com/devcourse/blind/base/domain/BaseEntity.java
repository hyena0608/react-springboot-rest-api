package com.devcourse.blind.base.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity {

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime deletedAt;

    public <T extends BaseEntity> void setUpBaseEntityBy(T domain) {
        this.createdAt = domain.getCreatedAt();
        this.updatedAt = domain.getUpdatedAt();
        this.deletedAt = domain.getDeletedAt();
    }

    public void setUpBaseEntityBy(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
