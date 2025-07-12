package com.example.advisor_backend.model.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ComboTest {
    @Test
    void testAllArgsConstructorAndGettersSetters() {
        Combo combo = new Combo("1", "组合1", "FOF", "中低", "策略A", "ACTIVE", new BigDecimal("0.12"), new BigDecimal("0.05"), 1000.0, LocalDateTime.now(), LocalDateTime.now(), "source", true, null);
        assertEquals("1", combo.getId());
        assertEquals("组合1", combo.getName());
        assertEquals("FOF", combo.getType());
        assertEquals("中低", combo.getRiskLevel());
        assertEquals("策略A", combo.getStrategy());
        assertEquals("ACTIVE", combo.getStatus());
        assertEquals(new BigDecimal("0.12"), combo.getAnnualizedReturn());
        assertEquals(new BigDecimal("0.05"), combo.getMaxDrawdown());
        assertEquals(1000.0, combo.getMinimumInvestment());
        assertEquals("source", combo.getSource());
        assertTrue(combo.isUserCreated());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        Combo combo = new Combo();
        combo.setId("2");
        combo.setName("组合2");
        combo.setType("CUSTOM");
        assertEquals("2", combo.getId());
        assertEquals("组合2", combo.getName());
        assertEquals("CUSTOM", combo.getType());
    }
} 