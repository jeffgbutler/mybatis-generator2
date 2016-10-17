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
package org.mybatis.generator2.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Jeff Butler
 */
public class Messages {
    private static final String BUNDLE_NAME = "org.mybatis.generator2.util.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public enum MessageId {
        TRACING_1("Tracing.1"), //$NON-NLS-1$
        TRACING_2("Tracing.2"), //$NON-NLS-1$
        TRACING_5("Tracing.5"), //$NON-NLS-1$
        TRACING_6("Tracing.6"), //$NON-NLS-1$
        RUNTIME_ERROR_6("RuntimeError.6"), //$NON-NLS-1$
        RUNTIME_ERROR_21("RuntimeError.21"), //$NON-NLS-1$
        WARNING_29("Warning.29"); //$NON-NLS-1$

        private String value;

        private MessageId(String value) {
            this.value = value;
        }
    }

    private Messages() {
    }

    public static String getString(MessageId key) {
        try {
            return RESOURCE_BUNDLE.getString(key.value);
        } catch (MissingResourceException e) {
            return '!' + key.value + '!';
        }
    }

    public static String getString(MessageId key, Object parm1) {
        return MessageFormat.format(getString(key), new Object[] { parm1 });
    }

    public static String getString(MessageId key, Object parm1, Object parm2) {
        return MessageFormat.format(getString(key), new Object[] { parm1, parm2 });
    }

    public static String getString(MessageId key, Object parm1, Object parm2, Object parm3) {
        return MessageFormat.format(getString(key), new Object[] { parm1, parm2, parm3 });
    }
}
