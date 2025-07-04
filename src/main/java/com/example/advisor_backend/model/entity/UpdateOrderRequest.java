package com.example.advisor_backend.model.entity;

public class UpdateOrderRequest {
    private Long id;
    private Long newParentId;
    private Integer newSortOrder;
    private String newTreeName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getNewParentId() { return newParentId; }
    public void setNewParentId(Long newParentId) { this.newParentId = newParentId; }

    public Integer getNewSortOrder() { return newSortOrder; }
    public void setNewSortOrder(Integer newSortOrder) { this.newSortOrder = newSortOrder; }

    public String getNewTreeName() { return newTreeName; }
    public void setNewTreeName(String newTreeName) { this.newTreeName = newTreeName; }
}