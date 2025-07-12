package com.example.advisor_backend.model.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class FundTest {
    @Test
    void testAllArgsConstructorAndGettersSetters() {
        Fund fund = new Fund();
        fund.setId(1L);
        fund.setCode("000001");
        fund.setName("测试基金");
        fund.setNav(new BigDecimal("1.23"));
        fund.setRisk(0.1);
        fund.setCompanyName("公司");
        fund.setManagerName("经理");
        fund.setInceptionDate(LocalDateTime.now());
        fund.setCreatedAt(LocalDateTime.now());
        fund.setUpdatedAt(LocalDateTime.now());
        assertEquals(1L, fund.getId());
        assertEquals("000001", fund.getCode());
        assertEquals("测试基金", fund.getName());
        assertNotNull(fund.getCreatedAt());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        Fund fund = new Fund();
        fund.setId(2L);
        fund.setCode("000002");
        fund.setName("基金2");
        assertEquals(2L, fund.getId());
        assertEquals("000002", fund.getCode());
        assertEquals("基金2", fund.getName());
    }
} 