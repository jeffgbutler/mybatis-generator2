package org.mybatis.generator2.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLog implements Log {
    
    private Logger log;
    
    public Slf4jLog(Class<?> clazz) {
        log = LoggerFactory.getLogger(clazz);
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s, e);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.debug(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }
    
    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }
    
    @Override
    public void trace(String s) {
        log.trace(s);
    }
}
