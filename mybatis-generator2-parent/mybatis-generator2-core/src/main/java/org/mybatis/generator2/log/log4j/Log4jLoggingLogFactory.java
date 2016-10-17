package org.mybatis.generator2.log.log4j;

import org.mybatis.generator2.log.AbstractLogFactory;
import org.mybatis.generator2.log.Log;

public class Log4jLoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> clazz) {
        return new Log4jLog(clazz);
    }
}