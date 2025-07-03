package com.example.advisor_backend.service;


import com.example.advisor_backend.model.dto.entity.FactorTreeNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FactorTreeService {

    List<FactorTreeNode> getAllNodes();
    List<FactorTreeNode> getTreeByType(String treeType);
    List<FactorTreeNode> getChildren(Long parentId);

    boolean addNode(FactorTreeNode node);
   
    boolean updateNode(FactorTreeNode node);

    boolean deleteNode(Long id);

    void moveNode(Long id, Long newParentId, Integer newSortOrder);
    void updateOrder(Long id, Long newParentId, Integer newSortOrder, String newTreeName);

    void importFromExcel(MultipartFile file) throws Exception;

    void renameTreeType(String treename, String newName);
    void deleteTreeType(String treename);

    List<FactorTreeNode> getAllNodesByType(String treeType);

    void renameTreeName(String treeType, String treeName, String newTreeName);
    void deleteTree(String treeType, String treeName);
}
