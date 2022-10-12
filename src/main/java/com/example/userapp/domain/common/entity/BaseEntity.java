package com.example.userapp.domain.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
    // TODO : sequence is better as it gets N number of ids
    // depending on design uuids are great and can be generated by the app so no need for a round trip to db
    // but you have to make sure java on server uses /dev/urandom or has good entropy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    protected LocalDateTime updatedAt;

    // optimistic locking to prevent from editing stale data.
    // we don't have to acquire locks or go crazy with isolation level
    // default in pg Read Committed so protects against dirty reads
    @Version
    private long version;
}