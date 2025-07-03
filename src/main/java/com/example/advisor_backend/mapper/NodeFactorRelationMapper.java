package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.dto.entity.NodeFactorRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface NodeFactorRelationMapper {
    // 查询一批节点下所有因子id
    List<Long> selectFactorIdsByNodeIds(@Param("nodeIds") List<Long> nodeIds);

    // 可选：插入、删除、单查等
    int insert(NodeFactorRelation relation);
    int deleteById(Long id);
    List<NodeFactorRelation> selectByNodeId(Long nodeId);
}