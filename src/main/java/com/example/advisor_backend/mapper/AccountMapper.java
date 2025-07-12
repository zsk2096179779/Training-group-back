package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.dto.AccountDTO;
import com.example.advisor_backend.model.dto.AccountDetailDTO;
import com.example.advisor_backend.model.entity.Account;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AccountMapper {

    // 查询账户列表，支持模糊搜索，返回 AccountDTO
    List<AccountDTO> findAccountListBySearch(@Param("search") String search);

    // 根据账户ID查询账户，返回 AccountDTO
    AccountDetailDTO findAccountById(@Param("accountId") Long accountId);
}
