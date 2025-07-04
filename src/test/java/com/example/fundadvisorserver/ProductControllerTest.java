package com.example.fundadvisorserver;

import com.example.fundadvisorserver.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    private ProductController productController;

    @Test
    void testList() {
        var list = productController.list();
        assertNotNull(list);
        assertTrue(list.size() >= 0);
    }

    @Test
    void testTransferOut() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", 1L);
        params.put("productId", 1L);
        params.put("ratio", 50.0);
        var resp = productController.transferOut(params);
        assertNotNull(resp);
        assertTrue(resp.containsKey("success"));
    }

    @Test
    void testTransferOutMissingParams() {
        Map<String, Object> params = new HashMap<>();
        // 缺少userId
        params.put("productId", 1L);
        params.put("ratio", 50.0);
        var resp = productController.transferOut(params);
        assertNotNull(resp);
        assertFalse((Boolean) resp.get("success"));
    }

    @Test
    void testTransferOutZeroRatio() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", 1L);
        params.put("productId", 1L);
        params.put("ratio", 0.0);
        var resp = productController.transferOut(params);
        assertNotNull(resp);
        assertTrue((Double) resp.get("amount") == 0.0);
    }

    @Test
    void testTransferOutOverAmount() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", 1L);
        params.put("productId", 1L);
        params.put("ratio", 1000.0); // 超过100%
        var resp = productController.transferOut(params);
        assertNotNull(resp);
        assertTrue((Double) resp.get("amount") >= 0.0); // 不应为负
    }
} 