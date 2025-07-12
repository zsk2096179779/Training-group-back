package com.example.advisor_backend.model.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {
    @Test
    void testOkWithData() {
        ApiResponse<String> resp = ApiResponse.ok("data");
        assertEquals(0, resp.getCode());
        assertEquals("success", resp.getMsg());
        assertEquals("data", resp.getData());
    }

    @Test
    void testError() {
        ApiResponse<String> resp = ApiResponse.error(400, "error");
        assertEquals(400, resp.getCode());
        assertEquals("error", resp.getMsg());
        assertNull(resp.getData());
    }

    @Test
    void testSettersAndGetters() {
        ApiResponse<String> resp = new ApiResponse<>();
        resp.setCode(1);
        resp.setMsg("msg");
        resp.setData("abc");
        assertEquals(1, resp.getCode());
        assertEquals("msg", resp.getMsg());
        assertEquals("abc", resp.getData());
    }

    @Test
    void testOkVoid() {
        ApiResponse<Void> resp = ApiResponse.ok();
        assertEquals(0, resp.getCode());
        assertEquals("success", resp.getMsg());
        assertNull(resp.getData());
    }
} 