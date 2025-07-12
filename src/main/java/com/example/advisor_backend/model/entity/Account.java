package com.example.advisor_backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
    @Id
    private Long accountId;

    private String accountName;

    // 其他字段和方法
}
