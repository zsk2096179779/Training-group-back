package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.mapper.FactorTreeNodeMapper;
import com.example.advisor_backend.model.entity.FactorTreeNode;
import com.example.advisor_backend.service.ServiceImpl.FactorTreeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FactorTreeServiceImplTest {
    @Mock
    private FactorTreeNodeMapper factorTreeNodeMapper;
    @InjectMocks
    private FactorTreeServiceImpl factorTreeService;

    @Test
    void getAllNodes() {
        when(factorTreeNodeMapper.selectAll()).thenReturn(Arrays.asList(new FactorTreeNode(), new FactorTreeNode()));
        List<FactorTreeNode> result = factorTreeService.getAllNodes();
        assertEquals(2, result.size());
    }

    @Test
    void addNode() {
        FactorTreeNode node = new FactorTreeNode();
        when(factorTreeNodeMapper.insert(node)).thenReturn(1);
        boolean result = factorTreeService.addNode(node);
        assertTrue(result);
    }

    @Test
    void updateNode() {
        FactorTreeNode node = new FactorTreeNode();
        when(factorTreeNodeMapper.update(node)).thenReturn(1);
        boolean result = factorTreeService.updateNode(node);
        assertTrue(result);
    }

    @Test
    void deleteNode() {
        when(factorTreeNodeMapper.deleteById(1L)).thenReturn(1);
        boolean result = factorTreeService.deleteNode(1L);
        assertTrue(result);
    }

    @Test
    void moveNode() {
        FactorTreeNode node = new FactorTreeNode();
        node.setId(1L);
        when(factorTreeNodeMapper.selectById(1L)).thenReturn(node);
        when(factorTreeNodeMapper.update(any(FactorTreeNode.class))).thenReturn(1);
        
        factorTreeService.moveNode(1L, 2L, 1);
        verify(factorTreeNodeMapper).update(any(FactorTreeNode.class));
    }
} 