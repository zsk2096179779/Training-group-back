package com.example.fundadvisorserver;

import com.example.fundadvisorserver.controller.ProductDetailController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductDetailControllerTest {
    @Autowired
    private ProductDetailController productDetailController;

    @Test
    void testGetProductDetail() {
        ResponseEntity<?> resp = productDetailController.getProductDetail(1L);
        assertNotNull(resp);
        assertEquals(200, resp.getStatusCodeValue());
    }

    @Test
    void testGetProductDetailNotFound() {
        ResponseEntity<?> resp = productDetailController.getProductDetail(99999L);
        assertNotNull(resp);
        assertEquals(404, resp.getStatusCodeValue());
    }

    @Test
    void testGetProductDetailWithNullId() {
        ResponseEntity<?> resp = productDetailController.getProductDetail(null);
        assertNotNull(resp);
        assertEquals(404, resp.getStatusCodeValue());
    }
} 