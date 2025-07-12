// 文件路径：src/main/java/com/example/advisor_backend/controller/StrategyManageController.java
package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.StrategyListRequest;
import com.example.advisor_backend.model.entity.Strategy;
import com.example.advisor_backend.model.entity.User;
import com.example.advisor_backend.service.ServiceImpl.UsersStrategyServiceImpl;
import com.example.advisor_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/strategy-management")
public class StrategyManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersStrategyServiceImpl strategyService;

    /**
     * 分页并可按名称/状态过滤地列出当前用户的所有策略
     * POST /api/strategy-management
     * 请求体：
     * {
     *   "page": 1,
     *   "limit": 10,
     *   "nameFilter": "xxx",
     *   "statusFilter": "active"
     * }
     */
    @PostMapping("")
    public ResponseEntity<?> listStrategies(
            Principal principal,
            @RequestBody StrategyListRequest req
    ) {
        // 1. 从 Principal 中取用户名
        String username = principal.getName();
        // 2. 根据用户名查用户
        User user = userService.getUserByName(username);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "用户不存在"));
        }

        // 3. 调用 Service 拿到分页+过滤后的结果
        //    （假设 service 已实现 getStrategiesByUserAndFilter(...) 方法）
        Page<Strategy> page = strategyService.getStrategiesByUserAndFilter(
                user,
                req.getPage(),
                req.getLimit(),
                req.getNameFilter(),
                req.getStatusFilter()
        );

        List<Strategy> list = page.getContent();
        long total = page.getTotalElements();

        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("records", list);
        resp.put("total", total);

        return ResponseEntity.ok(resp);
    }

    /**
     * 启动某条策略
     * POST /api/strategy-management/start
     * 请求体：{ "strategyId": 123 }
     */
    @PostMapping("/start")
    public ResponseEntity<?> startStrategy(@RequestBody Map<String, Integer> request) {
        Integer strategyId = request.get("strategyId");
        if (strategyId == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "缺少 strategyId 参数"));
        }
        try {
            strategyService.startStrategy(strategyId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "策略已启动"
            ));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "启动策略失败: " + ex.getMessage()));
        }
    }

    /**
     * 停止某条策略
     * POST /api/strategy-management/stop
     * 请求体：{ "strategyId": 123 }
     */
    @PostMapping("/stop")
    public ResponseEntity<?> stopStrategy(@RequestBody Map<String, Integer> request) {
        Integer strategyId = request.get("strategyId");
        if (strategyId == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "缺少 strategyId 参数"));
        }
        try {
            strategyService.stopStrategy(strategyId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "策略已停止"
            ));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "停止策略失败: " + ex.getMessage()));
        }
    }

    /**
     * 删除某条策略
     * POST /api/strategy-management/delete
     * 请求体：{ "strategyId": 123 }
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteStrategy(@RequestBody Map<String, Integer> request) {
        Integer strategyId = request.get("strategyId");
        if (strategyId == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "缺少 strategyId 参数"));
        }
        try {
            strategyService.deleteStrategy(strategyId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "策略已删除"
            ));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "删除策略失败: " + ex.getMessage()));
        }
    }

    /**
     * 新建一条策略
     * POST /api/strategy-management/new
     * 请求体：{ "username":"xxx","id":2 }
     * （这里仍在简化使用 User 对象，如需可改为专门的 CreateStrategyRequest）
     */
    @PostMapping("/new")
    public ResponseEntity<?> createStrategy(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "策略名称（username）不能为空"));
        }
        try {
            Strategy created = strategyService.createStrategy(user);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "策略创建成功",
                    "strategyId", created.getId(),
                    "strategyName", created.getName()
            ));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "创建策略失败: " + ex.getMessage()));
        }
    }
}
