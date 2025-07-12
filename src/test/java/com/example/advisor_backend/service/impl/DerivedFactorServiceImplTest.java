package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.mapper.DerivedFactorRelationMapper;
import com.example.advisor_backend.mapper.FundFactorMapper;
import com.example.advisor_backend.model.entity.FactorDerivation;
import com.example.advisor_backend.model.entity.FundFactor;
import com.example.advisor_backend.model.dto.CreateDerivedFactorRequest;
import com.example.advisor_backend.service.ServiceImpl.DerivedFactorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DerivedFactorServiceImplTest {
    @Mock
    private FundFactorMapper fundFactorMapper;
    
    @Mock
    private DerivedFactorRelationMapper derivedFactorRelationMapper;
    
    @InjectMocks
    private DerivedFactorServiceImpl derivedFactorService;

    @Test
    void createDerivedFactor() {
        // Given
        CreateDerivedFactorRequest request = new CreateDerivedFactorRequest();
        request.setName("测试衍生因子");
        request.setDescription("这是一个测试衍生因子");
        
        CreateDerivedFactorRequest.BaseFactorWeight weight1 = new CreateDerivedFactorRequest.BaseFactorWeight();
        weight1.setId(1L);
        weight1.setWeight(0.5);
        
        CreateDerivedFactorRequest.BaseFactorWeight weight2 = new CreateDerivedFactorRequest.BaseFactorWeight();
        weight2.setId(2L);
        weight2.setWeight(0.5);
        
        request.setBaseFactors(Arrays.asList(weight1, weight2));
        
        // Mock FundFactorMapper.insert 方法
        doAnswer(invocation -> {
            FundFactor factor = invocation.getArgument(0);
            factor.setId(100L); // 设置ID
            return 1;
        }).when(fundFactorMapper).insert(any(FundFactor.class));
        
        // Mock DerivedFactorRelationMapper.insertRelation 方法
        when(derivedFactorRelationMapper.insertRelation(anyLong(), anyLong(), anyDouble())).thenReturn(1);
        
        // When
        Long result = derivedFactorService.createDerivedFactor(request);
        
        // Then
        assertEquals(100L, result);
        verify(fundFactorMapper, times(1)).insert(any(FundFactor.class));
        verify(derivedFactorRelationMapper, times(2)).insertRelation(anyLong(), anyLong(), anyDouble());
    }
} 