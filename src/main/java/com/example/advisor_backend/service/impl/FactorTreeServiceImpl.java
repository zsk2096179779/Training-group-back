package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.mapper.FactorTreeNodeMapper;
import com.example.advisor_backend.model.dto.entity.FactorTreeNode;
import com.example.advisor_backend.service.FactorTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactorTreeServiceImpl implements FactorTreeService {

    @Autowired
    private FactorTreeNodeMapper mapper;

    @Override
    public List<FactorTreeNode> getAllNodes() {
        return mapper.selectAll();
    }

    @Override
    public List<FactorTreeNode> getChildren(Long parentId) {
        return mapper.selectByParentId(parentId);
    }

    @Override
    public boolean addNode(FactorTreeNode node) {
        return mapper.insert(node) > 0;
    }

    @Override
    public boolean updateNode(FactorTreeNode node) {
        return mapper.update(node) > 0;
    }

    @Override
    public boolean deleteNode(Long id) {
        return mapper.deleteById(id) > 0;
    }

    @Override
    public void moveNode(Long id, Long newParentId, Integer newSortOrder) {
        // 查询新父节点下所有子节点，按sortOrder排序
        List<FactorTreeNode> siblings = mapper.selectByParentId(newParentId);
        siblings.sort((a, b) -> a.getSortOrder() - b.getSortOrder());
        // 移除自身（如果在同一父节点下移动）
        siblings.removeIf(node -> node.getId().equals(id));
        // 插入到新位置
        FactorTreeNode movingNode = mapper.selectById(id);
        movingNode.setParentId(newParentId);
        movingNode.setSortOrder(newSortOrder);
        siblings.add(newSortOrder, movingNode);
        // 重新设置所有节点的sortOrder
        for (int i = 0; i < siblings.size(); i++) {
            siblings.get(i).setSortOrder(i);
            mapper.updateNodeParentAndSortOrder(siblings.get(i).getId(), siblings.get(i).getParentId(), siblings.get(i).getSortOrder());
        }
    }
}
