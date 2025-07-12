package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.mapper.FundFactorMapper;
import com.example.advisor_backend.model.entity.FundFactor;
import com.example.advisor_backend.service.ServiceImpl.FundFactorServiceImpl;
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
class FundFactorServiceImplTest {
    @Mock
    private FundFactorMapper fundFactorMapper;
    @InjectMocks
    private FundFactorServiceImpl fundFactorService;

    @Test
    void getAllFactors() {
        when(fundFactorMapper.selectAll()).thenReturn(Arrays.asList(new FundFactor(), new FundFactor()));
        List<FundFactor> result = fundFactorService.getAllFactors();
        assertEquals(2, result.size());
    }

    @Test
    void getFactorById() {
        FundFactor factor = new FundFactor();
        when(fundFactorMapper.selectById(1L)).thenReturn(factor);
        FundFactor result = fundFactorService.getFactorById(1L);
        assertNotNull(result);
    }

    @Test
    void getFactorsByNode() {
        when(fundFactorMapper.selectByNodeId(1L)).thenReturn(Arrays.asList(new FundFactor()));
        List<FundFactor> result = fundFactorService.getFactorsByNode(1L);
        assertEquals(1, result.size());
    }

    @Test
    void createFactor() {
        FundFactor factor = new FundFactor();
        when(fundFactorMapper.insert(factor)).thenReturn(1);
        boolean result = fundFactorService.createFactor(factor);
        assertTrue(result);
    }

    @Test
    void updateFactor() {
        FundFactor factor = new FundFactor();
        when(fundFactorMapper.update(factor)).thenReturn(1);
        boolean result = fundFactorService.updateFactor(factor);
        assertTrue(result);
    }

    @Test
    void deleteFactor() {
        when(fundFactorMapper.deleteById(1L)).thenReturn(1);
        boolean result = fundFactorService.deleteFactor(1L);
        assertTrue(result);
    }
} 