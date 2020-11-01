package com.roger.util;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtil {
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String logTime() {
        return DateFormatUtils.format(System.currentTimeMillis(), YYYY_MM_DD_HH_MM_SS_SSS);
    }
}
