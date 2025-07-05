package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.entity.Combo;
import com.example.advisor_backend.model.entity.Fund;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ComboMapper {
//    @Select("SELECT * FROM combos")
    List<Combo> list();
//    @Select("SELECT fund_code FROM combo_funds WHERE combo_id = #{id}")
    List<Integer> getFundId(Long id);

    List<Fund> getFunds(@Param("ids") List<Integer> ids); // 显式命名参数为 "ids"
//    多表查询
    @Select("SELECT * FROM funds")
    List<Fund> getFundsAll();


    void insertCombo(Combo combo);

    void insertComboFund(@Param("comboId") String comboId, @Param("fundCode") String fundCode, @Param("weight") BigDecimal weight);
}
