package com.example.advisor_backend.bean;

public class BacktestResponse {
    private double cumulativeReturn;
    private double maxDrawdown;
    private double sharpeRatio;
    private int trades;

    public BacktestResponse() {
    }

    public BacktestResponse(double cumulativeReturn, double maxDrawdown, double sharpeRatio, int trades) {
        this.cumulativeReturn = cumulativeReturn;
        this.maxDrawdown = maxDrawdown;
        this.sharpeRatio = sharpeRatio;
        this.trades = trades;
    }

    public double getCumulativeReturn() {
        return cumulativeReturn;
    }

    public void setCumulativeReturn(double cumulativeReturn) {
        this.cumulativeReturn = cumulativeReturn;
    }

    public double getMaxDrawdown() {
        return maxDrawdown;
    }

    public void setMaxDrawdown(double maxDrawdown) {
        this.maxDrawdown = maxDrawdown;
    }

    public double getSharpeRatio() {
        return sharpeRatio;
    }

    public void setSharpeRatio(double sharpeRatio) {
        this.sharpeRatio = sharpeRatio;
    }

    public int getTrades() {
        return trades;
    }

    public void setTrades(int trades) {
        this.trades = trades;
    }

    @Override
    public String toString() {
        return "BacktestResponse{" +
                "cumulativeReturn=" + cumulativeReturn +
                ", maxDrawdown=" + maxDrawdown +
                ", sharpeRatio=" + sharpeRatio +
                ", trades=" + trades +
                '}';
    }
}
