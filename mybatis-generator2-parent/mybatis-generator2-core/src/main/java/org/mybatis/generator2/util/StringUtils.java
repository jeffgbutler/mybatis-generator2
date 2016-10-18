package org.mybatis.generator2.util;

public interface StringUtils {

    static boolean stringHasValue(String s) {
        return s != null && s.trim().length() > 0;
    }

    static boolean stringContainsSQLWildcard(String s) {
        if (s == null) {
            return false;
        }

        return s.indexOf('%') != -1 || s.indexOf('_') != -1;
    }

    static boolean stringContainsSpace(String s) {
        return s != null && s.indexOf(' ') != -1;
    }
}
