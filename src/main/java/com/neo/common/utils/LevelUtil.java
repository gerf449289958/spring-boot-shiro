package com.neo.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

public class LevelUtil {

    static final String SEPARATOR = "/";

    static final String ROOT = "0";

    private LevelUtil() {
    }

    // 0
    // 0/1
    // 0/1/2
    // 0/1/3
    // 0/4
    public static String calculateLevel(String parentLevel, BigInteger parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId.toString());
        }
    }
}
