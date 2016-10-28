package org.mybatis.generator.test;

public enum TestEnum {
    FRED(1),
    WILMA(2),
    BARNEY(3),
    BETTY(4);

    private int value;

    private TestEnum(int value) {
        this.value = value;
    }
}