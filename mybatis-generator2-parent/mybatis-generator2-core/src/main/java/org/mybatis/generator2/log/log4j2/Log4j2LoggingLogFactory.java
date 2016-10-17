package org.mybatis.generator2.log.log4j2;

import org.mybatis.generator2.log.AbstractLogFactory;
import org.mybatis.generator2.log.Log;

public class Log4j2LoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> clazz) {
        return new Log4j2Log(clazz);
    }
}