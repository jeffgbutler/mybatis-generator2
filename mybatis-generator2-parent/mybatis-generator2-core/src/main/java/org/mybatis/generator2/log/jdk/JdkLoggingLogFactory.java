package org.mybatis.generator2.log.jdk;

import org.mybatis.generator2.log.AbstractLogFactory;
import org.mybatis.generator2.log.Log;

public class JdkLoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> clazz) {
        return new JdkLog(clazz);
    }
}