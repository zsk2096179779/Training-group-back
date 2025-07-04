package com.example.fundadvisorserver.controller;

import com.example.fundadvisorserver.entity.User;
import com.example.fundadvisorserver.repository.UserRepository;
import com.example.fundadvisorserver.repository.UserOtcStatusRepository;
import com.example.fundadvisorserver.repository.UserAgreementStatusRepository;
import com.example.fundadvisorserver.repository.KycQuestionnaireRepository;
import com.example.fundadvisorserver.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserOtcStatusRepository otcStatusRepository;
    @Autowired
    private UserAgreementStatusRepository agreementStatusRepository;
    @Autowired
    private KycQuestionnaireRepository kycQuestionnaireRepository;
    @Autowired
    private ProductRepository productRepository;

    // 注册接口
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String password = params.get("password");
        String nickname = params.get("nickname");
        System.out.println("[REGISTER] phone=" + phone + ", password=" + password + ", nickname=" + nickname);
        if (phone == null || phone.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("[REGISTER] 参数为空");
            return ResponseEntity.badRequest().body("手机号和密码不能为空");
        }
        if (phone.length() != 11 || !phone.matches("^1[3-9]\\d{9}$")) {
            System.out.println("[REGISTER] 手机号格式不正确: " + phone);
            return ResponseEntity.badRequest().body("手机号格式不正确");
        }
        if (userRepository.findByPhone(phone).isPresent()) {
            System.out.println("[REGISTER] 手机号已注册: " + phone);
            return ResponseEntity.badRequest().body("手机号已注册");
        }
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password); // 实际项目请加密
        user.setNickname(nickname);
        user.setRiskLevel("C3"); // 默认风险等级
        // 生成唯一资金账号（示例：U+时间戳+手机号后4位）
        String accountNo = "U" + System.currentTimeMillis() + phone.substring(Math.max(0, phone.length() - 4));
        user.setAccountNo(accountNo);
        userRepository.save(user);
        System.out.println("[REGISTER] 注册成功: " + phone + ", accountNo=" + accountNo);
        return ResponseEntity.ok("注册成功");
    }

    // 登录接口
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String password = params.get("password");
        if (phone == null || phone.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("手机号或密码错误");
        }
        System.out.println("[LOGIN] phone=" + phone + ", password=" + password);
        Optional<User> userOpt = userRepository.findByPhone(phone);
        System.out.println("[LOGIN] userOpt.isPresent()=" + userOpt.isPresent());
        if (userOpt.isPresent()) {
            System.out.println("[LOGIN] db password=" + userOpt.get().getPassword());
            if (userOpt.get().getPassword().equals(password)) {
                System.out.println("[LOGIN] 登录成功: " + phone);
                return ResponseEntity.ok(userOpt.get());
            } else {
                System.out.println("[LOGIN] 密码不匹配: 输入=" + password + ", 数据库=" + userOpt.get().getPassword());
            }
        } else {
            System.out.println("[LOGIN] 未找到手机号: " + phone);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("手机号或密码错误");
    }

    // 用户信息接口
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("nickname", user.getNickname());
            result.put("totalAsset", user.getTotalAsset());
            result.put("totalProfit", user.getTotalProfit());
            result.put("accountNo", user.getAccountNo());
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
    }

    // OTC开户状态接口
    @GetMapping("/otcStatus")
    public Map<String, Object> otcStatus(@RequestParam Long userId) {
        String status = otcStatusRepository.findByUserId(userId)
            .map(s -> s.getStatus())
            .orElse("未开户");
        return Map.of("otcStatus", status);
    }

    // 协议签署状态接口
    @GetMapping("/agreementStatus")
    public Map<String, Object> agreementStatus(@RequestParam Long userId) {
        String status = agreementStatusRepository.findByUserId(userId)
            .map(s -> s.getStatus())
            .orElse("未签署");
        return Map.of("agreementStatus", status);
    }

    // KYC状态接口
    @GetMapping("/kycStatus")
    public Map<String, Object> kycStatus(@RequestParam Long userId) {
        boolean completed = kycQuestionnaireRepository.findByUserId(userId)
            .map(k -> k.getCompleted())
            .orElse(false);
        return Map.of("kycStatus", completed);
    }

    // 适当性匹配检查接口
    @GetMapping("/suitabilityCheck")
    public Map<String, Object> suitabilityCheck(@RequestParam Long userId, @RequestParam Long productId) {
        // 查询用户
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Map.of("matched", false, "message", "用户不存在");
        }
        User user = userOpt.get();
        String userRiskLevel = user.getRiskLevel();
        // 查询产品
        com.example.fundadvisorserver.entity.Product product = null;
        try {
            product = productRepository.findById(productId).orElse(null);
        } catch (Exception e) {
            return Map.of("matched", false, "message", "适当性检查失败：产品不存在");
        }
        if (product == null) {
            return Map.of("matched", false, "message", "适当性检查失败：产品不存在");
        }
        String productRiskLevel = product.getRiskLevel();
        // 风险等级比较
        boolean matched = checkRiskMatch(userRiskLevel, productRiskLevel);
        String message = matched ? ("您的风险等级为" + userRiskLevel + "，可以购买该产品") : ("您的风险等级为" + userRiskLevel + "，不适合购买该产品（产品风险等级：" + productRiskLevel + ")");
        return Map.of(
            "matched", matched,
            "userRiskLevel", userRiskLevel,
            "productRiskLevel", productRiskLevel,
            "message", message
        );
    }

    // 风险等级比较逻辑
    private boolean checkRiskMatch(String userRisk, String productRisk) {
        if (userRisk == null || productRisk == null) return false;
        // 假设C1<C2<C3<C4<C5，R1<R2<R3<R4<R5
        java.util.Map<String, Integer> riskOrder = java.util.Map.of(
            "C1", 1, "C2", 2, "C3", 3, "C4", 4, "C5", 5,
            "R1", 1, "R2", 2, "R3", 3, "R4", 4, "R5", 5
        );
        return riskOrder.getOrDefault(userRisk, 0) >= riskOrder.getOrDefault(productRisk, 0);
    }
} 