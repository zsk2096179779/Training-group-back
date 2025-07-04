package com.example.fundadvisorserver;

import com.example.fundadvisorserver.controller.OrderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderControllerTest {
    @Autowired
    private OrderController orderController;

    @Test
    void testGetTotalAmount() {
        Map<String, Object> result = orderController.getTotalAmount(1L);
        assertNotNull(result);
        assertTrue(result.containsKey("totalAmount"));
    }

    @Test
    void testProductSummary() {
        var list = orderController.getProductSummary(1L);
        assertNotNull(list);
        assertTrue(list.size() >= 0);
    }

    @Test
    void testListOrders() {
        var list = orderController.listOrders(1L);
        assertNotNull(list);
    }

    @Test
    void testListOrdersWithInvalidUser() {
        var list = orderController.listOrders(99999L);
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void testProductSummaryWithInvalidUser() {
        var list = orderController.getProductSummary(99999L);
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }
} 