package com.example.advisor_backend.model.dto.entity;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 公募基金因子实体类
 */
@Data
public class FundFactor {
    private Long id;
    private String name;
    private String code;
    private String category;
    private String description;
    private String type; // 因子类型，普通/衍生
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
