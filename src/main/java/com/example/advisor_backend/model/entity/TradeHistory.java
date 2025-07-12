package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "trade_history")
@Data
public class TradeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trade_time", nullable = false)
    private LocalDateTime tradeTime;

    @Column(name = "stock_code", nullable = false, length = 10)
    private String stockCode;

    @Column(name = "stock_name", nullable = false, length = 50)
    private String stockName;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false, length = 10)
    private OperationType operationType;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal fee;

    @Column(name = "strategy_id")
    private Integer strategyId;

    // 操作类型枚举
    public enum OperationType {
        BUY, SELL
    }

    public TradeHistory() {
    }

    public TradeHistory(Long id, LocalDateTime tradeTime, String stockCode, String stockName, OperationType operationType, BigDecimal price, Integer quantity, BigDecimal amount, BigDecimal fee, Integer strategyId) {
        this.id = id;
        this.tradeTime = tradeTime;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.operationType = operationType;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.fee = fee;
        this.strategyId = strategyId;
    }

    @Override
    public String toString() {
        return "TradeHistory{" +
                "id=" + id +
                ", tradeTime=" + tradeTime +
                ", stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", operationType=" + operationType +
                ", price=" + price +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", fee=" + fee +
                ", strategyId=" + strategyId +
                '}';
    }
}
