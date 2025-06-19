package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.dto.entity.FactorTreeNode;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface FactorTreeNodeMapper {

    List<FactorTreeNode> selectAll();

    List<FactorTreeNode> selectByParentId(Long parentId);

    FactorTreeNode selectById(Long id);

    int insert(FactorTreeNode node);

    int update(FactorTreeNode node);

    int deleteById(Long id);

    int updateNodeParentAndSortOrder(Long id, Long newParentId, Integer newSortOrder);

    int updateSortOrderBatch(List<FactorTreeNode> nodes);
}