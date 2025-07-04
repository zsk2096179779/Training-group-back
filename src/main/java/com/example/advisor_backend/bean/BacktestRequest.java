package com.example.advisor_backend.bean;

import java.time.LocalDate;

public class BacktestRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private int initialCapital;
    private double transactionFee;
    private double slippage;

    public BacktestRequest() {
    }

    public BacktestRequest(LocalDate startDate, LocalDate endDate, int initialCapital, double transactionFee, double slippage) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.initialCapital = initialCapital;
        this.transactionFee = transactionFee;
        this.slippage = slippage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getInitialCapital() {
        return initialCapital;
    }

    public void setInitialCapital(int initialCapital) {
        this.initialCapital = initialCapital;
    }

    public double getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(double transactionFee) {
        this.transactionFee = transactionFee;
    }

    public double getSlippage() {
        return slippage;
    }

    public void setSlippage(double slippage) {
        this.slippage = slippage;
    }

    @Override
    public String toString() {
        return "BacktestRequest{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", initialCapital=" + initialCapital +
                ", transactionFee=" + transactionFee +
                ", slippage=" + slippage +
                '}';
    }
}
