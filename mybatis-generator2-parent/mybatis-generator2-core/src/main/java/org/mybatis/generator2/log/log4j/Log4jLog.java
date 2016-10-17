/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator2.log.log4j;

import java.util.function.Supplier;

import org.apache.log4j.Logger;
import org.mybatis.generator2.log.Log;

/**
 * 
 * @author Clinton Begin
 * 
 */
public class Log4jLog implements Log {

    private Logger log;

    public Log4jLog(Class<?> clazz) {
        log = Logger.getLogger(clazz);
    }

    @Override
    public void error(Supplier<String> s, Throwable e) {
        log.error(s.get(), e);
    }

    @Override
    public void error(Supplier<String> s) {
        log.error(s.get());
    }

    @Override
    public void debug(Supplier<String> s) {
        if (log.isDebugEnabled()) {
            log.debug(s.get());
        }
    }

    @Override
    public void warn(Supplier<String> s) {
        log.warn(s.get());
    }

    @Override
    public void trace(Supplier<String> s) {
        if (log.isTraceEnabled()) {
            log.trace(s.get());
        }
    }
}
