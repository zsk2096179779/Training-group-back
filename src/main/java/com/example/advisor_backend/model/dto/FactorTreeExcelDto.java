package com.example.advisor_backend.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for exporting Factor Tree data to Excel.
 */
@Data
public class FactorTreeExcelDto {

    @ExcelProperty(value = "节点名称", index = 0)
    private String name;

    @ExcelProperty(value = "树类型", index = 1)
    private String treeType;

    @ExcelProperty(value = "排序", index = 2)
    private Integer sortOrder;

    @ExcelProperty(value = "节点路径", index = 3)
    private String path;

    @ExcelProperty(value = "树名称", index = 4)
    private String treeName;
} 