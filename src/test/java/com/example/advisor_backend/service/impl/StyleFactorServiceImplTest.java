package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.mapper.StyleFactorMapper;
import com.example.advisor_backend.model.entity.StyleFactor;
import com.example.advisor_backend.service.ServiceImpl.StyleFactorServiceImpl;
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
class StyleFactorServiceImplTest {
    @Mock
    private StyleFactorMapper styleFactorMapper;
    @InjectMocks
    private StyleFactorServiceImpl styleFactorService;

    @Test
    void getAll() {
        when(styleFactorMapper.selectAll()).thenReturn(Arrays.asList(new StyleFactor(), new StyleFactor()));
        List<StyleFactor> result = styleFactorService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    void create() {
        StyleFactor factor = new StyleFactor();
        when(styleFactorMapper.insert(factor)).thenReturn(1);
        styleFactorService.create(factor);
        verify(styleFactorMapper).insert(factor);
    }

    @Test
    void delete() {
        styleFactorService.delete(1L);
        verify(styleFactorMapper).deleteById(1L);
    }
} 