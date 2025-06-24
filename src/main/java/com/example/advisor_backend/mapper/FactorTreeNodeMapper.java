package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.dto.entity.FactorTreeNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FactorTreeNodeMapper {
    List<Long> selectChildrenIds(@Param("parentId") Long parentId);
    void updateParentAndOrder(@Param("id") Long id,
                              @Param("parentId") Long parentId,
                              @Param("sortOrder") Integer sortOrder);

    void reorderSiblings(@Param("parentId") Long parentId);
    List<FactorTreeNode> selectByTreeType(String treeType);
    List<FactorTreeNode> selectAll();

    List<FactorTreeNode> selectByParentId(Long parentId);

    FactorTreeNode selectById(Long id);

    int insert(FactorTreeNode node);

    int update(FactorTreeNode node);

    int deleteById(Long id);

    int updateNodeParentAndSortOrder(Long id, Long newParentId, Integer newSortOrder);

    int updateSortOrderBatch(List<FactorTreeNode> nodes);

    void renameTreeType(@Param("treeType") String treeType, @Param("newName") String newName);
    void deleteByTreeType(@Param("treeType") String treeType);

    void renameTreeName(@Param("treeType") String treeType, @Param("treeName") String treeName, @Param("newTreeName") String newTreeName);
    void deleteByTreeTypeAndTreeName(@Param("treeType") String treeType, @Param("treeName") String treeName);
}