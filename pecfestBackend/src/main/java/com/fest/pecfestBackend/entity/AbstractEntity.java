package com.fest.pecfestBackend.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class AbstractEntity<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private T id;

    @Column
    private LocalDateTime updatedAt;

    public AbstractEntity() {
        this.updatedAt = LocalDateTime.now();
    }
}
