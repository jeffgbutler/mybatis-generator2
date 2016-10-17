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
package org.mybatis.generator2.log.jdk;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mybatis.generator2.log.Log;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class JdkLog implements Log {

    private Logger log;

    public JdkLog(Class<?> clazz) {
        log = Logger.getLogger(clazz.getName());
    }

    @Override
    public void error(Supplier<String> s, Throwable e) {
        log.log(Level.SEVERE, e, s);
    }

    @Override
    public void error(Supplier<String> s) {
        log.severe(s);
    }

    @Override
    public void debug(Supplier<String> s) {
        log.fine(s);
    }

    @Override
    public void warn(Supplier<String> s) {
        log.warning(s);
    }
    
    @Override
    public void trace(Supplier<String> s) {
        log.finest(s);
    }
}
