package com.example.advisor_backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateFundRequest {
    @NotBlank private String name;
    @Size(max=1000) private String description;


}

