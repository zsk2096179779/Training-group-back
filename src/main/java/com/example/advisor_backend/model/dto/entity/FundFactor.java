package com.example.advisor_backend.model.dto.entity;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 公募基金因子实体类
 */
@Data
public class FundFactor {
    private Long id;
    private String name;
    private String code;
    private String category;
    private String description;
    private String type; // 因子类型，普通/衍生
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String formula; // 衍生因子公式
    // 非数据库字段，仅用于前端交互
    private java.util.List<BaseFactorRef> baseFactors;

    // getter/setter for formula and baseFactors
    public String getFormula() { return formula; }
    public void setFormula(String formula) { this.formula = formula; }
    public java.util.List<BaseFactorRef> getBaseFactors() { return baseFactors; }
    public void setBaseFactors(java.util.List<BaseFactorRef> baseFactors) { this.baseFactors = baseFactors; }

    // BaseFactorRef 内部类
    public static class BaseFactorRef {
        private Long baseFactorId;
        private java.math.BigDecimal weight;
        public Long getBaseFactorId() { return baseFactorId; }
        public void setBaseFactorId(Long baseFactorId) { this.baseFactorId = baseFactorId; }
        public java.math.BigDecimal getWeight() { return weight; }
        public void setWeight(java.math.BigDecimal weight) { this.weight = weight; }
    }
}
