package org.mybatis.generator2.log.slf4j;

import java.util.function.Supplier;

import org.mybatis.generator2.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLog implements Log {
    
    private Logger log;
    
    public Slf4jLog(Class<?> clazz) {
        log = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void error(Supplier<String> s, Throwable e) {
        if (log.isErrorEnabled()) {
            log.error(s.get(), e);
        }
    }

    @Override
    public void error(Supplier<String> s) {
        if (log.isErrorEnabled()) {
            log.error(s.get());
        }
    }

    @Override
    public void debug(Supplier<String> s) {
        if (log.isDebugEnabled()) {
            log.debug(s.get());
        }
    }

    @Override
    public void warn(Supplier<String> s) {
        if (log.isWarnEnabled()) {
            log.warn(s.get());
        }
    }
    
    @Override
    public void trace(Supplier<String> s) {
        if (log.isTraceEnabled()) {
            log.trace(s.get());
        }
    }
}
