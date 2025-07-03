package com.example.advisor_backend.model.dto.entity;


import java.io.Serializable;
import java.util.List;

/**
 * 数据传输对象 (Data Transfer Object) for Factors.
 * 这个类专门用于在前端和后端之间传递因子数据。
 * 它包含了因子的所有基本信息，以及一个用于表示衍生因子构成的嵌套列表。
 */
public class FactorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String code;
    private String category;
    private String description;

    // 关键字段: '普通' 或 '衍生'
    private String type;

    // 关键字段: 用于承载衍生因子的基础因子列表
    // 当 type = '普通' 时, 这个列表将为 null 或空.
    // 当 type = '衍生' 时, 这个列表将包含基础因子和它们的权重.
    private List<BaseFactorComponent> baseFactors;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BaseFactorComponent> getBaseFactors() {
        return baseFactors;
    }

    public void setBaseFactors(List<BaseFactorComponent> baseFactors) {
        this.baseFactors = baseFactors;
    }


    /**
     * 内部静态类，用于清晰地表示衍生因子的每一个成分。
     * 这样做比使用 Map<Long, Double> 更健壮、更易于理解。
     */
    public static class BaseFactorComponent implements Serializable {

        private static final long serialVersionUID = 1L;

        // 基础因子的ID
        private Long baseFactorId;

        // 该基础因子对应的权重
        private Double weight;

        // --- Getters and Setters ---

        public Long getBaseFactorId() {
            return baseFactorId;
        }

        public void setBaseFactorId(Long baseFactorId) {
            this.baseFactorId = baseFactorId;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }
    }
}