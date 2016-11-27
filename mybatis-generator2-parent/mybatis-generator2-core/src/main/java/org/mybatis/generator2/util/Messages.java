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
        RUNTIME_ERROR_4("RuntimeError.4"), //$NON-NLS-1$
        RUNTIME_ERROR_5("RuntimeError.5"), //$NON-NLS-1$
        RUNTIME_ERROR_6("RuntimeError.6"), //$NON-NLS-1$
        RUNTIME_ERROR_13("RuntimeError.13"), //$NON-NLS-1$
        RUNTIME_ERROR_14("RuntimeError.14"), //$NON-NLS-1$
        RUNTIME_ERROR_15("RuntimeError.15"), //$NON-NLS-1$
        RUNTIME_ERROR_16("RuntimeError.16"), //$NON-NLS-1$
        RUNTIME_ERROR_17("RuntimeError.17"), //$NON-NLS-1$
        RUNTIME_ERROR_21("RuntimeError.21"), //$NON-NLS-1$
        VALIDATION_ERROR_0("ValidationError.0"), //$NON-NLS-1$
        VALIDATION_ERROR_1("ValidationError.1"), //$NON-NLS-1$
        VALIDATION_ERROR_2("ValidationError.2"), //$NON-NLS-1$
        VALIDATION_ERROR_3("ValidationError.3"), //$NON-NLS-1$
        VALIDATION_ERROR_4("ValidationError.4"), //$NON-NLS-1$
        VALIDATION_ERROR_5("ValidationError.5"), //$NON-NLS-1$
        VALIDATION_ERROR_6("ValidationError.6"), //$NON-NLS-1$
        VALIDATION_ERROR_7("ValidationError.7"), //$NON-NLS-1$
        VALIDATION_ERROR_8("ValidationError.8"), //$NON-NLS-1$
        VALIDATION_ERROR_10("ValidationError.10"), //$NON-NLS-1$
        VALIDATION_ERROR_11("ValidationError.11"), //$NON-NLS-1$
        VALIDATION_ERROR_12("ValidationError.12"), //$NON-NLS-1$
        VALIDATION_ERROR_14("ValidationError.14"), //$NON-NLS-1$
        VALIDATION_ERROR_15("ValidationError.15"), //$NON-NLS-1$
        VALIDATION_ERROR_16("ValidationError.16"), //$NON-NLS-1$
        VALIDATION_ERROR_17("ValidationError.17"), //$NON-NLS-1$
        VALIDATION_ERROR_18("ValidationError.18"), //$NON-NLS-1$
        VALIDATION_ERROR_19("ValidationError.19"), //$NON-NLS-1$
        VALIDATION_ERROR_20("ValidationError.20"), //$NON-NLS-1$
        VALIDATION_ERROR_21("ValidationError.21"), //$NON-NLS-1$
        VALIDATION_ERROR_22("ValidationError.22"), //$NON-NLS-1$
        VALIDATION_ERROR_23("ValidationError.23"), //$NON-NLS-1$
        VALIDATION_ERROR_24("ValidationError.24"), //$NON-NLS-1$
        VALIDATION_ERROR_26("ValidationError.26"), //$NON-NLS-1$
        VALIDATION_ERROR_27("ValidationError.27"), //$NON-NLS-1$
        WARNING_7("Warning.7"), //$NON-NLS-1$
        WARNING_29("Warning.29"); //$NON-NLS-1$

        private String value;

        private MessageId(String value) {
            this.value = value;
        }
    }

    private Messages() {
    }

    public static String getString(MessageId key) {
        return RESOURCE_BUNDLE.getString(key.value);
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
