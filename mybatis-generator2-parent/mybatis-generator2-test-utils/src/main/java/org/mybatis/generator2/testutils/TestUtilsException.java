package org.mybatis.generator2.testutils;

public class TestUtilsException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 694317372069523222L;

    public TestUtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestUtilsException(String message) {
        super(message);
    }
}
