package com.example.fundadvisorserver;

import com.example.fundadvisorserver.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    @Autowired
    private UserController userController;

    @Test
    void testRegisterAndLogin() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", "18888889999");
        params.put("password", "test123");
        params.put("nickname", "testuser");
        ResponseEntity<?> regResp = userController.register(params);
        assertNotNull(regResp);
        // 登录
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("phone", "18888889999");
        loginParams.put("password", "test123");
        ResponseEntity<?> loginResp = userController.login(loginParams);
        assertNotNull(loginResp);
        assertEquals(200, loginResp.getStatusCodeValue());
    }

    @Test
    void testGetUserInfo() {
        ResponseEntity<?> resp = userController.getUserInfo(1L);
        assertNotNull(resp);
    }

    @Test
    void testSuitabilityCheck() {
        Map<String, Object> result = userController.suitabilityCheck(1L, 1L);
        assertNotNull(result);
        assertTrue(result.containsKey("matched"));
    }

    @Test
    void testRegisterWithEmptyPhone() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", "");
        params.put("password", "test123");
        ResponseEntity<?> resp = userController.register(params);
        assertEquals(400, resp.getStatusCodeValue());
    }

    @Test
    void testRegisterDuplicatePhone() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", "18888889999");
        params.put("password", "test123");
        params.put("nickname", "testuser");
        userController.register(params); // 第一次注册
        ResponseEntity<?> resp = userController.register(params); // 重复注册
        assertEquals(400, resp.getStatusCodeValue());
    }

    @Test
    void testLoginWithWrongPassword() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", "18888889999");
        params.put("password", "wrong");
        ResponseEntity<?> resp = userController.login(params);
        assertEquals(401, resp.getStatusCodeValue());
    }

    @Test
    void testGetUserInfoNotFound() {
        ResponseEntity<?> resp = userController.getUserInfo(99999L);
        assertEquals(404, resp.getStatusCodeValue());
    }

    @Test
    void testSuitabilityCheckNotMatch() {
        // 假设userId=1为C3, productId=5为R5
        Map<String, Object> result = userController.suitabilityCheck(1L, 5L);
        assertNotNull(result);
        assertTrue(result.containsKey("matched"));
        assertFalse((Boolean) result.get("matched"));
    }

    @Test
    void testRegisterWithNullPassword() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", "19999999999");
        params.put("password", null);
        ResponseEntity<?> resp = userController.register(params);
        assertEquals(400, resp.getStatusCodeValue());
    }

    @Test
    void testRegisterWithShortPhone() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", "123"); // 非法手机号
        params.put("password", "test123");
        ResponseEntity<?> resp = userController.register(params);
        assertEquals(400, resp.getStatusCodeValue());
    }

    @Test
    void testLoginWithEmptyPhone() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", "");
        params.put("password", "test123");
        ResponseEntity<?> resp = userController.login(params);
        assertEquals(401, resp.getStatusCodeValue());
    }

    @Test
    void testSuitabilityCheckWithNullProduct() {
        Map<String, Object> result = userController.suitabilityCheck(1L, 99999L);
        assertNotNull(result);
        assertFalse((Boolean) result.get("matched"));
        assertFalse(result.get("message").toString().contains("产品不存在"));
    }
} 