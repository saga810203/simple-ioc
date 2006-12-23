package com.star.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串帮助类。
 * 
 * @author liuwei
 * @version 1.0
 */
public class StringUtils {

    /**
     * 字符串分割。如： (string)zhangsan => [zhangsan,string] zhangsan =>
     * [zhangsan,null]
     * 
     * @param s
     *            被分割的字符串
     * @return
     */
    public static String[] split1(String s) {
        String[] result = new String[2];
        result[0] = s;
        if (s.charAt(0) == '(') {
            int index = s.indexOf(')');
            if (index > 0) {
                result[1] = s.substring(1, index);
                result[0] = s.substring(index + 1);
            }
        }
        return result;
    }

    /**
     * 字符串分割。如： id(BeanId) => [id,BeanId] id => [id,null]
     * 
     * @param s
     *            被分割的字符串
     * @return
     */
    public static String[] splite2(String s) {
        String[] result = new String[2];
        result[0] = s;
        if (s.charAt(s.length() - 1) == ')') {
            int index = s.indexOf('(');
            if (index > 0) {
                result[0] = s.substring(0, index);
                result[1] = s.substring(index + 1, s.length() - 1);
            }
        }
        return result;
    }

    /**
     * 分割字符串得到属性键值对。 格式如： id:1, name:liuwei
     * 
     * @param s
     * @return
     */
    public static Map<String, String> spliteProperties(String s) {
        Map<String, String> m = new HashMap<String, String>();
        String[] kvStrs = s.split(",");
        for (int i = 0; i < kvStrs.length; i++) {
            String kvStr = kvStrs[i];
            String[] kv = spliteKeyValue(kvStr);
            m.put(kv[0].trim(), kv[1].trim());
        }
        return m;
    }

    private static String[] spliteKeyValue(String s) {
        int index = s.indexOf(':');
        if (index == 0) {
            throw new RuntimeException("splite error, source='" + s + "' regex=':'");
        }
        String k = s.substring(0, index);
        String v = s.substring(index + 1);

        return new String[] { k, v };
    }

    public static int parseToInt(String s, int defaultValue) {
        if (s == null)
            return defaultValue;
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Trim leading whitespace from the given String.
     * 
     * @param str
     *            the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    public static String trimLeadingWhitespace(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
            buf.deleteCharAt(0);
        }
        return buf.toString();
    }

    /**
     * Trim trailing whitespace from the given String.
     * 
     * @param str
     *            the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    public static String trimTrailingWhitespace(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }
}
