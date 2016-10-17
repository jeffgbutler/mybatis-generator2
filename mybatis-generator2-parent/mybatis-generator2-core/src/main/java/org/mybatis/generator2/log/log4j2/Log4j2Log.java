package org.mybatis.generator2.log.log4j2;

import org.apache.logging.log4j.Logger;
import org.mybatis.generator2.log.Log;

import java.util.function.Supplier;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class Log4j2Log implements Log {
    
    private Logger log;

    public Log4j2Log(Class<?> clazz) {
        log = LogManager.getLogger(clazz);
    }

    @Override
    public void error(Supplier<String> s, Throwable e) {
        if (log.isEnabled(Level.ERROR)) {
            log.error(s.get(), e);
        }
    }

    @Override
    public void error(Supplier<String> s) {
        if (log.isEnabled(Level.ERROR)) {
            log.error(s.get());
        }
    }

    @Override
    public void debug(Supplier<String> s) {
        if (log.isEnabled(Level.DEBUG)) {
            log.debug(s.get());
        }
    }

    @Override
    public void warn(Supplier<String> s) {
        if (log.isEnabled(Level.WARN)) {
            log.warn(s.get());
        }
    }
    
    @Override
    public void trace(Supplier<String> s) {
        if (log.isEnabled(Level.TRACE)) {
            log.trace(s.get());
        }
    }
}
