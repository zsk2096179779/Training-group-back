package com.example.fundadvisorserver.controller;

import com.example.fundadvisorserver.entity.Order;
import com.example.fundadvisorserver.entity.Product;
import com.example.fundadvisorserver.repository.OrderRepository;
import com.example.fundadvisorserver.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/buy")
    public Map<String, Object> buy(
        @RequestHeader("userId") Long userId,
        @RequestBody Map<String, Object> params
    ) {
        Long productId = Long.valueOf(params.get("productId").toString());
        Double amount = Double.valueOf(params.get("amount").toString());
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setAmount(amount);
        order.setStatus("已下单");
        order.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        orderRepository.save(order);
        return Map.of("msg", "下单成功", "orderId", order.getId());
    }

    @PostMapping("/create")
    public Map<String, Object> createOrder(
        @RequestHeader("userId") Long userId,
        @RequestBody Map<String, Object> params
    ) {
        Long productId = Long.valueOf(params.get("productId").toString());
        Double amount = Double.valueOf(params.get("amount").toString());
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setAmount(amount);
        order.setStatus("已下单");
        order.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        orderRepository.save(order);
        return Map.of("msg", "下单成功", "orderId", order.getId(), "success", true);
    }

    @GetMapping("/hasBought")
    public Map<String, Object> hasBought(@RequestParam Long userId, @RequestParam Long productId) {
        boolean bought = orderRepository.existsByUserIdAndProductId(userId, productId);
        return Map.of("bought", bought);
    }

    @GetMapping("/list")
    public List<Map<String, Object>> listOrders(@RequestParam Long userId) {
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<Map<String, Object>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Order order : orders) {
            Map<String, Object> map = new HashMap<>();
            map.put("productId", order.getProductId());
            map.put("amount", order.getAmount());
            map.put("createdAt", order.getCreatedAt() != null ? sdf.format(order.getCreatedAt()) : null);
            map.put("status", order.getStatus());
            // 联查产品信息
            Product product = productRepository.findById(order.getProductId()).orElse(null);
            if (product != null) {
                map.put("productName", product.getName());
                map.put("productType", product.getType());
            }
            result.add(map);
        }
        return result;
    }

    @GetMapping("/totalAmount")
    public Map<String, Object> getTotalAmount(@RequestParam Long userId) {
        double buyTotal = 0.0;
        double outTotal = 0.0;
        List<Order> allOrders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        for (Order order : allOrders) {
            if (order.getOrderType() == null || "买入".equals(order.getOrderType())) {
                buyTotal += order.getAmount();
            } else if ("转出".equals(order.getOrderType())) {
                outTotal += order.getAmount();
            }
        }
        double total = buyTotal - outTotal;
        return Map.of("totalAmount", total);
    }

    @GetMapping("/productSummary")
    public List<Map<String, Object>> getProductSummary(@RequestParam Long userId) {
        // 查询该用户所有订单
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        // 按产品分组累计金额
        Map<Long, Double> productBuyAmountMap = new HashMap<>();
        Map<Long, Double> productOutAmountMap = new HashMap<>();
        Map<Long, Product> productMap = new HashMap<>();
        for (Order order : orders) {
            Long productId = order.getProductId();
            // 只统计买入订单
            if (order.getOrderType() == null || "买入".equals(order.getOrderType())) {
                productBuyAmountMap.put(productId, productBuyAmountMap.getOrDefault(productId, 0.0) + order.getAmount());
            }
            // 统计转出订单
            if ("转出".equals(order.getOrderType())) {
                productOutAmountMap.put(productId, productOutAmountMap.getOrDefault(productId, 0.0) + order.getAmount());
            }
            // 记录产品信息
            if (!productMap.containsKey(productId)) {
                Product product = productRepository.findById(productId).orElse(null);
                if (product != null) {
                    productMap.put(productId, product);
                }
            }
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (Long productId : productBuyAmountMap.keySet()) {
            Double buyAmount = productBuyAmountMap.getOrDefault(productId, 0.0);
            Double outAmount = productOutAmountMap.getOrDefault(productId, 0.0);
            Double totalAmount = buyAmount - outAmount;
            if (totalAmount < 0) totalAmount = 0.0; // 不允许为负
            Product product = productMap.get(productId);
            if (product != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("productId", productId);
                map.put("productName", product.getName());
                map.put("productType", product.getType());
                map.put("totalAmount", totalAmount);
                map.put("riskLevel", product.getRiskLevel());
                result.add(map);
            }
        }
        return result;
    }
} 