package com.example.advisor_backend.model.dto;

import java.util.List;

public class PreviewFormulaRequest {
    private String formula;
    private List<BaseFactorRef> baseFactors;

    public String getFormula() { return formula; }
    public void setFormula(String formula) { this.formula = formula; }

    public List<BaseFactorRef> getBaseFactors() { return baseFactors; }
    public void setBaseFactors(List<BaseFactorRef> baseFactors) { this.baseFactors = baseFactors; }
}