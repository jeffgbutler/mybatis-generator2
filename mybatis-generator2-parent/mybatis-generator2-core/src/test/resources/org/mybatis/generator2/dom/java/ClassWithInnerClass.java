package org.mybatis.generator.test;

import java.math.BigDecimal;

public class TestClass {
    private BigDecimal amount;

    private TestClass() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public static class Builder {
        private testClass TestClass = new TestClass();

        public Builder withAmount(amount BigDecimal) {
            testClass.amount = amount;
            return this;
        }

        public TestClass build() {
            return testClass;
        }
    }
}
