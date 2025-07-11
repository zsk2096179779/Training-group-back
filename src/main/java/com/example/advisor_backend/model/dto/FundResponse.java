// src/main/java/com/example/advisor_backend/model/dto/FundResponse.java
package com.example.advisor_backend.model.dto;

import com.example.advisor_backend.model.entity.Fund;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FundResponse {
    private String code;
    private String name;
    private BigDecimal nav;
    private Double risk;
    private String managerName;
    private List<LabelResponse> labels;  // 只输出 label.name
    private String companyName;
    private LocalDate inceptionDate;
}
