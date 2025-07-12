// src/main/java/com/example/advisor_backend/repository/FundRepository.java
package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.dto.FundDTO;
import com.example.advisor_backend.model.entity.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface FundRepository
        extends JpaRepository<Fund, Long>,
        JpaSpecificationExecutor<Fund> {
    Optional<Fund> findByCode(String code);
    // SpecificationExecutor 用于动态过滤
    // 通过过滤条件查询基金列表，支持分页
    // 修改查询方法，通过基金名称和类型来过滤
    // 使用 name 和 companyName 进行模糊查询
    List<Fund> findByNameContainingAndCompanyNameContaining(String nameFilter, String companyNameFilter);
}
