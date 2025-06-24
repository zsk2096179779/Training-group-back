package com.example.advisor_backend.model.dto.entity;

public class NodeFactorRelation {
    private Long id;
    private Long nodeId;
    private Long factorId;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getNodeId() { return nodeId; }
    public void setNodeId(Long nodeId) { this.nodeId = nodeId; }

    public Long getFactorId() { return factorId; }
    public void setFactorId(Long factorId) { this.factorId = factorId; }
}