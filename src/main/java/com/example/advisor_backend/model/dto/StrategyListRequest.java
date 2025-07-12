// 文件路径：src/main/java/com/example/advisor_backend/model/dto/StrategyListRequest.java
package com.example.advisor_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StrategyListRequest {
    /** 当前页 */
    private Integer page;
    /** 每页大小 */
    private Integer limit;
    /** 名称过滤 */
    private String nameFilter;
    /** 状态过滤 */
    private String statusFilter;

}
