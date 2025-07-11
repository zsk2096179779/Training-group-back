// src/main/java/com/example/advisor_backend/model/dto/FundFilterRequest.java
package com.example.advisor_backend.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import java.util.List;

@Data
@Getter @Setter
public class FundFilterRequest {
    private String code;             // 模糊匹配代码或名称
    private List<Long> labels;       // 标签ID 列表
    private String companyName;
    private String managerName;

    @Min(1)
    private int page = 1;
    @Min(1)
    private int size = 20;
}
