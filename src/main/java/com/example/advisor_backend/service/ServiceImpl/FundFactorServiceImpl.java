package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.mapper.FactorTreeNodeMapper;
import com.example.advisor_backend.mapper.FundFactorMapper;
import com.example.advisor_backend.mapper.NodeFactorRelationMapper;
import com.example.advisor_backend.mapper.DerivedFactorRelationMapper;
import com.example.advisor_backend.model.entity.FundFactor;
import com.example.advisor_backend.model.entity.FactorDerivation;
import com.example.advisor_backend.service.FundFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FundFactorServiceImpl implements FundFactorService {

    @Autowired
    private FactorTreeNodeMapper factorTreeNodeMapper;
    @Autowired
    private NodeFactorRelationMapper nodeFactorRelationMapper;
    @Autowired
    private FundFactorMapper fundFactorMapper;
    @Autowired
    private DerivedFactorRelationMapper factorDerivationMapper;

    // 递归查找所有子节点id（含自己）
    public List<Long> getAllSubNodeIds(Long nodeId) {
        List<Long> result = new ArrayList<>();
        result.add(nodeId);
        List<Long> children = factorTreeNodeMapper.selectChildrenIds(nodeId);
        for (Long childId : children) {
            result.addAll(getAllSubNodeIds(childId));
        }
        return result;
    }

    // 查找所有子节点下的因子
    public List<FundFactor> getFactorsByNodeRecursive(Long nodeId) {
        List<Long> allNodeIds = getAllSubNodeIds(nodeId);
        if (allNodeIds.isEmpty()) return new ArrayList<>();
        List<Long> factorIds = nodeFactorRelationMapper.selectFactorIdsByNodeIds(allNodeIds);
        if (factorIds.isEmpty()) return new ArrayList<>();
        return fundFactorMapper.selectByIds(factorIds);
    }
    @Override
    public List<FundFactor> getFactorsByNode(Long nodeId) {
        return fundFactorMapper.selectByNodeId(nodeId);
    }

    @Override
    public List<FundFactor> getAllFactors() {
        return fundFactorMapper.selectAll();
    }

    @Override
    public FundFactor getFactorById(Long id) {
        return fundFactorMapper.selectById(id);
    }

    @Override
    public boolean createFactor(FundFactor factor) {
        // 自动生成 code
        if (factor.getCode() == null || factor.getCode().trim().isEmpty()) {
            factor.setCode("F" + java.util.UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        }
        if ("衍生".equals(factor.getType())) {
            // 1. 保存主表
            fundFactorMapper.insert(factor);
            // 2. 保存衍生因子与基础因子的关系
            if (factor.getBaseFactors() != null) {
                for (FundFactor.BaseFactorRef ref : factor.getBaseFactors()) {
                    FactorDerivation derivation = new FactorDerivation();
                    derivation.setFactorId(factor.getId());
                    derivation.setBaseFactorId(ref.getBaseFactorId());
                    derivation.setWeight(ref.getWeight());
                    factorDerivationMapper.insert(derivation);
                }
            }
            return true;
        } else {
            // 普通因子
            return fundFactorMapper.insert(factor) > 0;
        }
    }

    @Override
    public boolean updateFactor(FundFactor factor) {
        return fundFactorMapper.update(factor) > 0;
    }

    @Override
    public boolean deleteFactor(Long id) {
        return fundFactorMapper.deleteById(id) > 0;
    }
}
