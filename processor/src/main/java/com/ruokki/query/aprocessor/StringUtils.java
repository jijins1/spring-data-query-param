package com.ruokki.query.aprocessor;

/**
 * String utils for helping developpers
 */
public class StringUtils {

    /**
     * Capitalize a string.
     *
     * @param s the string to capitalize
     * @return the String
     */
    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

}
