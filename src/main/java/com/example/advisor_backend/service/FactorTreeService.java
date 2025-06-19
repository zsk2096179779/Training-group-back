package com.example.advisor_backend.service;


import com.example.advisor_backend.model.dto.entity.FactorTreeNode;
import java.util.List;

public interface FactorTreeService {

    List<FactorTreeNode> getAllNodes();

    List<FactorTreeNode> getChildren(Long parentId);

    boolean addNode(FactorTreeNode node);

    boolean updateNode(FactorTreeNode node);

    boolean deleteNode(Long id);

    void moveNode(Long id, Long newParentId, Integer newSortOrder);
}
