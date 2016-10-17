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
package org.mybatis.generator2.log;

import org.mybatis.generator2.log.jdk.JdkLoggingLogFactory;
import org.mybatis.generator2.log.log4j.Log4jLoggingLogFactory;
import org.mybatis.generator2.log.log4j2.Log4j2LoggingLogFactory;
import org.mybatis.generator2.log.slf4j.Slf4jLoggingLogFactory;

/**
 * Factory for creating loggers. Uses runtime introspection to determine the
 * AbstractLogFactory implementation.
 * 
 * @author Jeff Butler
 * 
 */
public class LogFactory {
    private static AbstractLogFactory abstractLogFactory;

    static {
        trySlf4j();
        tryLog4j2();
        tryLog4j();
        tryJdk();
    }
    
    private static void trySlf4j() {
        tryCandidate(Slf4jLoggingLogFactory.class);
    }
    
    private static void tryLog4j2() {
        tryCandidate(Log4j2LoggingLogFactory.class);
    }

    private static void tryLog4j() {
        tryCandidate(Log4jLoggingLogFactory.class);
    }

    private static void tryJdk() {
        tryCandidate(JdkLoggingLogFactory.class);
    }

    private static void tryCandidate(Class<? extends AbstractLogFactory> candidate) {
        if (abstractLogFactory != null) {
            return;
        }
        
        try {
            AbstractLogFactory candidateFactory = candidate.newInstance();
            Log log = candidateFactory.getLog(LogFactory.class);
            log.debug(() -> "Initialized Logging with " + candidate.getName());
            abstractLogFactory = candidateFactory;
        } catch (Exception e) {
            // ignore - try the next thing
        }
    }

    public static Log getLog(Class<?> clazz) {
        return abstractLogFactory.getLog(clazz);
    }

    /**
     * This method will switch the logging implementation to Java native
     * logging. This is useful in situations where you want to use Java native
     * logging to log activity but Log4J is on the classpath. Note that
     * this method is only effective for log classes obtained after calling this
     * method. If you intend to use this method you should call it before
     * calling any other method.
     */
    public static void forceJavaLogging() {
        abstractLogFactory = new JdkLoggingLogFactory();
    }

    public static void setLogFactory(AbstractLogFactory logFactory) {
        LogFactory.abstractLogFactory = logFactory;
    }
}
