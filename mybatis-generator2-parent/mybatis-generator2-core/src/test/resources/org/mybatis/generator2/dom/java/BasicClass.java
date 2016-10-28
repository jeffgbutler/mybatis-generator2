package org.mybatis.generator.test;

import java.math.BigDecimal;

public class TestClass {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public static int add(int a, int b) {
        return a + b;
    }
}
