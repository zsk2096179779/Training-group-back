package com.example.advisor_backend.model.dto;

import com.example.advisor_backend.model.entity.Fund;
import lombok.*;

@Data
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class FundDTO {
    private Long id;
    private String code;
    private String name;
    private String companyName;

    public FundDTO(Fund fund) {
        this.id = fund.getId();
        this.code = fund.getCode();
        this.name = fund.getName();
        this.companyName = fund.getCompanyName();
    }
}
