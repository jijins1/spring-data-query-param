package com.ruokki.query.aprocessor;

public class StringUtils {

    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

}