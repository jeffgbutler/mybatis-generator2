package org.mybatis.generator.test;

public enum BasicEnum {
    FRED(1),
    WILMA(2),
    BARNEY(3),
    BETTY(4);

    private int value;

    private BasicEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
