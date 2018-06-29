package com.simplexx.wnp.baselib.util;

import java.util.Iterator;

/**
 * Created by fan-gk on 2017/4/19.
 */

public class StringUtil {
    public static final String EMPTY = "";

    public static boolean isNullOrEmpty(String value) {
        return value == null || EMPTY.equals(value);
    }

    public static boolean isNullOrWhiteSpace(String value) {
        return value == null || EMPTY.equals(value.trim());
    }

    public static String firstOrDefaultNotNullOrWhiteSpace(String... values) {
        if (values == null || values.length == 0) return null;
        for (String value : values) {
            if (!isNullOrWhiteSpace(value))
                return value;
        }
        return null;
    }

    public static String capitalize(String value) {
        if (value == null)
            return value;
        if (value.length() == 1)
            return value.toUpperCase();
        return value.substring(0, 1).toUpperCase()
                + value.substring(1);
    }

    public static String padLeft(String text, int totalWidth, char paddingChar) {
        if (text == null)
            text = EMPTY;

        if (text.length() >= totalWidth)
            return text;

        int paddingLength = totalWidth - text.length();
        char[] paddingChars = new char[paddingLength];
        for (int i = 0; i < paddingChars.length; i++) {
            paddingChars[i] = paddingChar;
        }
        return new String(paddingChars) + text;
    }

    /**
     * 比较两个字符串是否相等，任意一个为null都返回false
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        return str1 != null && str2 != null && str1.equals(str2);
    }

    public static boolean isEqualLength(String text, int length) {
        if (text != null)
            return text.length() == length ? true : false;
        else
            return false;
    }

    public static String join(CharSequence delimiter, Iterable<?> tokens) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = tokens.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(delimiter);
                sb.append(it.next());
            }
        }
        return sb.toString();
    }

    public static String ensureNotNull(String str) {
        return isNullOrEmpty(str) ? StringUtil.EMPTY : str;
    }

    public static Boolean equal(String text1, String text2) {
        return defaultString(text1).equals(defaultString(text2));
    }

    public static String defaultString(String str) {
        return defaultString(str, EMPTY);
    }

    public static String defaultString(String str, String defaultString) {
        return str == null ? defaultString : str;
    }
}
