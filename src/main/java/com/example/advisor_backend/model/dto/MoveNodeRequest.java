package com.example.advisor_backend.model.dto;

import lombok.Data;

@Data
public class MoveNodeRequest {
    private Long id;           // 被拖拽节点ID
    private Long newParentId;  // 新父节点ID
    private Integer newSortOrder; // 新顺序（同级下第几个，从0开始）
} 