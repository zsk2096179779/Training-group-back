package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.entity.FactorTreeNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactorTreeNodeRepository extends JpaRepository<FactorTreeNode, Long> {
    List<FactorTreeNode> findByTreeType(String treeType);
    // 其他方法...
}