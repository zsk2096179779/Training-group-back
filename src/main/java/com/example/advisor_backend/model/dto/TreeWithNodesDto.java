package com.example.advisor_backend.model.dto;

import com.example.advisor_backend.model.dto.entity.FactorTreeNode;
import java.util.List;

public class TreeWithNodesDto {
    private String treeName;
    private List<FactorTreeNode> nodes;

    public String getTreeName() {
        return treeName;
    }
    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }
    public List<FactorTreeNode> getNodes() {
        return nodes;
    }
    public void setNodes(List<FactorTreeNode> nodes) {
        this.nodes = nodes;
    }
} 