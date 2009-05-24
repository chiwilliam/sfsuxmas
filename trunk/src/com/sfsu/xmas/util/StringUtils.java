package com.sfsu.xmas.util;

public class StringUtils {

    public static String clean(String s) {
        if (s != null) {
            s = s.trim();
            char[] chars = new char[]{
                //' ',
                '/', '\\',
                '[', ']',
                '{', '}',
                '&',
                '%',
                '!',
                '*',
//                '(', ')',
                '|',
//                '-',
                '\'',
                ';'
            };
            for (int i = 0; i < chars.length; i++) {
                s = s.replace(chars[i], '_');
            }
            s = s.replace("\"", "");
        }
        return s;
    }
}
