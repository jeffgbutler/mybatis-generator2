package org.mybatis.generator2.util;

/**
 * This set of utility methods is implemented as an interface so that static imports
 * aren't necessary.  Simply implement this interface and all the utility methods
 * become available.
 * 
 * @author Jeff Butler
 *
 */
public interface StringUtils {

    default boolean stringHasValue(String s) {
        return s != null && s.trim().length() > 0;
    }

    default boolean stringContainsSQLWildcard(String s) {
        if (s == null) {
            return false;
        }

        return s.indexOf('%') != -1 || s.indexOf('_') != -1;
    }

    default boolean stringContainsSpace(String s) {
        return s != null && s.indexOf(' ') != -1;
    }
}
