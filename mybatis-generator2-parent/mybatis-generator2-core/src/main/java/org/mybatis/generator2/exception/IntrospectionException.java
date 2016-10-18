package org.mybatis.generator2.exception;

public class IntrospectionException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1485339948081962217L;

    public IntrospectionException(String message) {
        super(message);
    }

    public IntrospectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
