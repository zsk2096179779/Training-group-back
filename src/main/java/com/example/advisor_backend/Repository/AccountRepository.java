package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.dto.AccountDTO;
import com.example.advisor_backend.model.dto.AccountDetailDTO;
import com.example.advisor_backend.model.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    // 根据账户ID查找账户
    Account findByAccountId(Long accountId);

    // 添加其他查询账户的方法
}
