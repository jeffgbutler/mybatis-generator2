package org.mybatis.generator2.util;

public class StringUtils {

    public static boolean stringHasValue(String s) {
        return s != null && s.length() > 0;
    }

    public static boolean stringContainsSQLWildcard(String s) {
        if (s == null) {
            return false;
        }

        return s.indexOf('%') != -1 || s.indexOf('_') != -1;
    }

    public static boolean stringContainsSpace(String s) {
        return s != null && s.indexOf(' ') != -1;
    }
}
