// 文件路径: src/main/java/com/example/advisor_backend/model/dto/StrategyDetailRequest.java
package com.example.advisor_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StrategyDetailRequest {
    /** 唯一标识当前登录用户的用户名 */
    private String username;
    /** 要查看/请求的策略 ID */
    private Long strategyId;

}
