package org.mybatis.generator.test;

import java.io.Serializable;

public interface BasicInterface extends Serializable {
    int value = 0;

    static int getValue() {
        return value;
    }

    default int add(int a, int b) {
        return a + b;
    }

    int doSomething();
}
