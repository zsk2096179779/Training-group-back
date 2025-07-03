// src/main/java/com/example/advisor_backend/model/dto/RegisterRequest.java
package com.example.advisor_backend.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
public class RegisterRequest {
    @NotBlank(message="用户名不能为空")
    private String username;

    @Email(message="邮箱格式不正确")
    @NotBlank(message="邮箱不能为空")
    private String email;

    @Size(min=6, message="密码至少6位")
    private String password;

    @Size(min=6, message="确认密码至少6位")
    private String confirmPassword;

    @AssertTrue(message="两次密码不一致")
    public boolean isPasswordsMatch() {
        return password != null && password.equals(confirmPassword);
    }

}
