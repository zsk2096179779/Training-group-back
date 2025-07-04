package com.example.fundadvisorserver.controller;

import com.example.fundadvisorserver.entity.Product;
import com.example.fundadvisorserver.entity.Order;
import com.example.fundadvisorserver.repository.ProductRepository;
import com.example.fundadvisorserver.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/list")
    public List<Product> list() {
        return productRepository.findAll();
    }

    @PostMapping("/transferOut")
    public Map<String, Object> transferOut(@RequestBody Map<String, Object> params) {
        Object userIdObj = params.get("userId");
        Object productIdObj = params.get("productId");
        Object ratioObj = params.get("ratio");
        if (userIdObj == null || productIdObj == null || ratioObj == null) {
            return Map.of("success", false, "msg", "参数缺失");
        }
        Long userId = Long.valueOf(userIdObj.toString());
        Long productId = Long.valueOf(productIdObj.toString());
        Double ratio = Double.valueOf(ratioObj.toString());
        // 查询累计买入金额
        List<String> buyTypes = new ArrayList<>();
        buyTypes.add(null);
        buyTypes.add("买入");
        List<Order> buyOrders = orderRepository.findByUserIdAndProductIdAndOrderTypeIn(userId, productId, buyTypes);
        double totalBuyAmount = buyOrders.stream().mapToDouble(Order::getAmount).sum();
        // 查询累计转出金额
        List<Order> outOrders = orderRepository.findByUserIdAndProductIdAndOrderTypeIn(userId, productId, java.util.Collections.singletonList("转出"));
        double totalOutAmount = outOrders.stream().mapToDouble(Order::getAmount).sum();
        // 可转出金额
        double availableAmount = totalBuyAmount - totalOutAmount;
        if (availableAmount < 0) availableAmount = 0.0;
        // 计算本次转出金额
        double amount = availableAmount * ratio / 100.0;
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setAmount(amount);
        order.setStatus("转出申请中");
        order.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        order.setOrderType("转出");
        orderRepository.save(order);
        return Map.of("msg", "转出申请已提交", "orderId", order.getId(), "success", true, "amount", amount);
    }
} 