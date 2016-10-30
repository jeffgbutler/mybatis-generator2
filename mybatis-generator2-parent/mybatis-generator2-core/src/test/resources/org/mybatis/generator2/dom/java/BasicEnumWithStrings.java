package org.mybatis.generator.test;

public enum BasicEnumWithStrings {
    FRED("1"),
    WILMA("2"),
    BARNEY("3"),
    BETTY("4");

    private String value;

    private BasicEnumWithStrings(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
