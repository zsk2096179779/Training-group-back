// src/main/java/com/example/advisor_backend/model/dto/LabelResponse.java
package com.example.advisor_backend.model.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Data @Getter @AllArgsConstructor
public class LabelResponse {
    private Long id;
    private String name;
}
