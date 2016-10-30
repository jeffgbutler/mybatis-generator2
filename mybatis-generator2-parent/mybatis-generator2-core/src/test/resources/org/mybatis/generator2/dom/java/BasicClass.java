package org.mybatis.generator.test;

import java.io.Serializable;
import java.math.BigDecimal;

public class BasicClass implements Serializable {
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

    /**
     * This is a method that exercises a lot of the renderer
     * 
     * @throws Exception
     */
    public void tortureTestMethod() throws Exception {
        boolean bool = false;

        if (bool) {
            System.out.println("It's true!");
        } else {
            System.out.println("It's false!");
        }

        int number = 0;

        switch (number) {
        case 0:
        case 1:
            System.out.println("It's zero or one!");
            break;
        case 2:
            System.out.println("It's two!");
            break;

        default:
            System.out.println("It's not zero or one or two!");
        }

        System.out.println("End of method");
    }
}
