// src/main/java/com/example/advisor_backend/model/entity/Fund.java
package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "fund")
@Getter @Setter @NoArgsConstructor
public class Fund {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length=20)
    private String code;

    @Column(nullable = false, length=200)
    private String name;

    // 最新净值
    private BigDecimal nav;

    // 风险指标
    private Double risk;

    private String companyName;

    @Column(length = 100)
    private String managerName;

    @Column(name = "inception_date")
    private LocalDateTime inceptionDate;

    @Column(name = "created_at",
            nullable = false,
            columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)")
    private LocalDateTime createdAt;

    @Column(name = "updated_at",
            nullable = false,
            columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "fund_label",
            joinColumns = @JoinColumn(name = "fund_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels;

    @PrePersist
    public void prePersist(){
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "fund", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NavHistory> navHistory;

    @OneToMany(mappedBy = "fund", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FundHolding> fundHoldings;

    @OneToMany(mappedBy = "fund", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attribution> attribution;

    @OneToMany(mappedBy = "fund", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Announcement> announcements;

}
