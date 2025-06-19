package com.example.advisor_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fund {
    @Id
    @Column(length = 100, nullable = false, unique = true)
    private String fundCode; // 主键：基金代码

    @Column(length = 100, nullable = false)
    private String fundName;

    @Column(length = 50)
    private String category;

    @Column(updatable = false)
    private LocalDateTime createTime;

    @Column
    private LocalDateTime updateTime;
}
