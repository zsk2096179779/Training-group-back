// src/main/java/com/example/advisor_backend/model/dto/PageResponse.java
package com.example.advisor_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class PageResult<T> {
    private List<T> items;
    private long total;
}
