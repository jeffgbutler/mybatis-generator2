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
package org.mybatis.generator2.render;

/**
 * The Class OutputUtilities.
 *
 * @author Jeff Butler
 */
public interface OutputUtilities {
    
    /**
     * Utility method that indents the buffer by the default amount for Java
     * (four spaces per indent level).
     * 
     * @param buffer
     *            a StringBuilder to append to
     * @param indentLevel
     *            the required indent level
     */
    static void javaIndent(StringBuilder buffer, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            buffer.append("    "); //$NON-NLS-1$
        }
    }

    /**
     * Utility method that indents the buffer by the default amount for XML (two
     * spaces per indent level).
     * 
     * @param buffer
     *            a StringBuilder to append to
     * @param indentLevel
     *            the required indent level
     */
    static void xmlIndent(StringBuilder buffer, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            buffer.append("  "); //$NON-NLS-1$
        }
    }

    /**
     * Utility method. Adds a newline character to a StringBuilder.
     * 
     * @param buffer
     *            the StringBuilder to be appended to
     */
    static void newLine(StringBuilder buffer) {
        buffer.append(System.lineSeparator());
    }
}
