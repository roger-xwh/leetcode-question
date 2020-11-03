package com.roger.entity;

import java.math.BigDecimal;

public class ExecuteResult {
    private BigDecimal useTime;
    private BigDecimal useMemory;
    private long caseCount;
    private BigDecimal passRate;

    public BigDecimal getUseTime() {
        return useTime;
    }

    public BigDecimal getUseMemory() {
        return useMemory;
    }

    public long getCaseCount() {
        return caseCount;
    }

    public BigDecimal getPassRate() {
        return passRate;
    }

    public static ExecuteResult builder() {
        return new ExecuteResult();
    }

    public ExecuteResult useTime(BigDecimal useTime) {
        this.useTime = useTime;
        return this;
    }

    public ExecuteResult useMemory(BigDecimal useMemory) {
        this.useMemory = useMemory;
        return this;
    }

    public ExecuteResult caseCount(int caseCount) {
        this.caseCount = caseCount;
        return this;
    }

    public ExecuteResult passRate(BigDecimal passRate) {
        this.passRate = passRate;
        return this;
    }
}
