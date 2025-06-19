package com.example.advisor_backend.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.DocFlavor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Combo {
    @Id
    @Column(length = 20, nullable = false, unique = true)
    private String id; // 主键：组合ID

    @Column(length = 100, nullable = false)
    private String name;

    //    @Enumerated(EnumType.STRING) // 枚举存储为字符串
    @Column
    private String type; // 建议改为枚举类型（如FOF/CUSTOM）

    @Column
    private String riskLevel;

    @Column(length = 100)
    private String strategy;

    //    @Enumerated(EnumType.STRING) // 枚举存储为字符串
    @Column
    private String status;

    @Column(precision = 5, scale = 2)
    private BigDecimal annualizedReturn;

    @Column(precision = 5, scale = 2)
    private BigDecimal maxDrawdown;

    @Column
    private Integer minimumInvestment;

    @Column(updatable = false)
    private LocalDateTime createTime;

    @Column
    private LocalDateTime updateTime;

    @ManyToMany
    @JoinTable(
            name = "product_funds", // 中间表名
            joinColumns = @JoinColumn(name = "product_id"), // 当前表外键
            inverseJoinColumns = @JoinColumn(name = "fund_code") // 关联表外键
    )
    private List<Fund> funds; // 多对多关联基金
}
