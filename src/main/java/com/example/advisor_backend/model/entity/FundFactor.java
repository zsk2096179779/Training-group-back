package com.example.advisor_backend.model.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 公募基金因子实体类
 */
@Entity
@Table(name = "fund_factor")
@Data
public class FundFactor {
    @Id
    private Long id;
    private String name;
    private String code;
    private String category;
    private String description;
    private String type; // 因子类型，普通/衍生
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    // getter/setter for formula and baseFactors
    @Getter
    private String formula; // 衍生因子公式
    // 非数据库字段，仅用于前端交互
    @Getter@Transient
    private List<BaseFactorRef> baseFactors;


    // BaseFactorRef 内部类
    @Setter
    @Getter
    public static class BaseFactorRef {
        private Long baseFactorId;
        private java.math.BigDecimal weight;

    }
}
