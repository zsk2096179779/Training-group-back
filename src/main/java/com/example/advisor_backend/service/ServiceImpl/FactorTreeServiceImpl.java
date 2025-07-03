package com.example.advisor_backend.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.example.advisor_backend.mapper.FactorTreeNodeMapper;
import com.example.advisor_backend.model.dto.FactorTreeExcelDto;
import com.example.advisor_backend.model.dto.entity.FactorTreeNode;
import com.example.advisor_backend.repository.FactorTreeNodeRepository;
import com.example.advisor_backend.service.FactorTreeService;
import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.Comparator;

@Service
public class FactorTreeServiceImpl implements FactorTreeService {

    private static final Logger logger = LoggerFactory.getLogger(FactorTreeServiceImpl.class);

    @Autowired
    private FactorTreeNodeMapper factorTreeNodeMapper;

    @Autowired
    private FactorTreeNodeRepository nodeRepository;


    @Override
    public List<FactorTreeNode> getTreeByType(String treeType) {
        List<FactorTreeNode> allNodes = factorTreeNodeMapper.selectByTreeType(treeType);

        Map<Long, FactorTreeNode> nodeMap = new HashMap<>();
        List<FactorTreeNode> roots = new ArrayList<>();

        for (FactorTreeNode node : allNodes) {
            node.setChildren(new ArrayList<>());
            nodeMap.put(node.getId(), node);
        }

        for (FactorTreeNode node : allNodes) {
            if (node.getParentId() == null || node.getParentId() == 0L) {
                roots.add(node);
            } else {
                FactorTreeNode parent = nodeMap.get(node.getParentId());
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }
        // Sort children recursively
        roots.forEach(this::sortChildren);
        return roots;
    }

    private void sortChildren(FactorTreeNode node) {
        if (node.getChildren() != null && !node.getChildren().isEmpty()) {
            node.getChildren().sort(Comparator.comparing(FactorTreeNode::getSortOrder, Comparator.nullsLast(Comparator.naturalOrder())));
            node.getChildren().forEach(this::sortChildren);
        }
    }

    @Override
    public List<FactorTreeNode> getAllNodes() {
        return factorTreeNodeMapper.selectAll();
    }

    @Override
    public List<FactorTreeNode> getChildren(Long parentId) {
        return factorTreeNodeMapper.selectByParentId(parentId);
    }

    @Override
    public boolean addNode(FactorTreeNode node) {
        return factorTreeNodeMapper.insert(node) > 0;
    }

    @Override
    public boolean updateNode(FactorTreeNode node) {
        return factorTreeNodeMapper.update(node) > 0;
    }

    @Override
    public boolean deleteNode(Long id) {
        return factorTreeNodeMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public void moveNode(Long id, Long newParentId, Integer newSortOrder) {
        FactorTreeNode movingNode = factorTreeNodeMapper.selectById(id);
        if (movingNode != null) {
            movingNode.setParentId(newParentId);
            movingNode.setSortOrder(newSortOrder);
            factorTreeNodeMapper.update(movingNode);
        }
    }

    @Override
    @Transactional
    public void importFromExcel(MultipartFile file) throws Exception {
        List<FactorTreeExcelDto> nodeList;
        try {
            nodeList = EasyExcel.read(
                file.getInputStream(),
                FactorTreeExcelDto.class,
                null
            ).sheet().doReadSync();
        } catch (Exception e) {
            throw new RuntimeException("文件格式错误，请使用标准模板导入！");
        }

        if (nodeList == null || nodeList.isEmpty()) {
            throw new RuntimeException("文件内容为空，请检查模板和数据！");
        }

        // 按路径长度升序排序，保证父节点先插入
        nodeList.sort(Comparator.comparingInt(dto -> dto.getPath().split("/").length));
        // 路径->数据库id 映射
        Map<String, Long> pathToId = new HashMap<>();

        for (int i = 0; i < nodeList.size(); i++) {
            FactorTreeExcelDto dto = nodeList.get(i);
            if (dto.getName() == null || dto.getName().trim().isEmpty()) {
                throw new RuntimeException("第" + (i + 2) + "行：节点名称不能为空！");
            }
            if (dto.getTreeType() == null || dto.getTreeType().trim().isEmpty()) {
                throw new RuntimeException("第" + (i + 2) + "行：树类型不能为空！");
            }
            if (dto.getPath() == null || dto.getPath().trim().isEmpty()) {
                throw new RuntimeException("第" + (i + 2) + "行：节点路径不能为空！");
            }
            String parentPath = dto.getPath().contains("/") ? dto.getPath().substring(0, dto.getPath().lastIndexOf("/")) : null;
            Long parentId = (parentPath == null || parentPath.isEmpty()) ? 0L : pathToId.get(parentPath);
            FactorTreeNode node = new FactorTreeNode();
            node.setId(null);
            node.setName(dto.getName());
            node.setParentId(parentId);
            node.setTreeType(dto.getTreeType());
            node.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);
            node.setTreeName(dto.getTreeName());
            factorTreeNodeMapper.insert(node);
            // 获取插入后的id
            pathToId.put(dto.getPath(), node.getId());
        }
    }

    @Override
    public void renameTreeType(String treename, String newName) {
        factorTreeNodeMapper.renameTreeType(treename, newName);
    }

    @Override
    public void deleteTreeType(String treename) {
        factorTreeNodeMapper.deleteByTreeType(treename);
    }

    @Override
    public List<FactorTreeNode> getAllNodesByType(String treeType) {
        return factorTreeNodeMapper.selectByTreeType(treeType);
    }

    @Override
    public void renameTreeName(String treeType, String treeName, String newTreeName) {
        factorTreeNodeMapper.renameTreeName(treeType, treeName, newTreeName);
    }

    @Override
    public void deleteTree(String treeType, String treeName) {
        factorTreeNodeMapper.deleteByTreeTypeAndTreeName(treeType, treeName);
    }

    @Override
    @Transactional
    public void updateOrder(Long id, Long newParentId, Integer newSortOrder, String newTreeName) {
        FactorTreeNode node = factorTreeNodeMapper.selectById(id);
        if (node != null) {
            if (newTreeName != null && !newTreeName.equals(node.getTreeName())) {
                node.setTreeName(newTreeName);
            }
            node.setParentId(newParentId);
            node.setSortOrder(newSortOrder);
            node.setUpdateTime(java.time.LocalDateTime.now());
            factorTreeNodeMapper.update(node);
        }
    }
}