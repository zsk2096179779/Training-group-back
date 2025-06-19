package com.example.advisor_backend.model.dto.entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FactorTreeNode {
    private Long id;
    private String name;
    private Long parentId;
    private String treeType;
    private Integer sortOrder;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
