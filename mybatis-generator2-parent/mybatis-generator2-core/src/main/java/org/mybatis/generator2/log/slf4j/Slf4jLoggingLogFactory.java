package org.mybatis.generator2.log.slf4j;

import org.mybatis.generator2.log.AbstractLogFactory;
import org.mybatis.generator2.log.Log;

public class Slf4jLoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> clazz) {
        return new Slf4jLog(clazz);
    }
}