package com.roger.util;

public final class LogUtil {
    public static void logWithFlag(String message) {
        if (ThreadLocalUtil.instance().getLogFlag()) {
            System.out.println(message);
        }
    }

    public static void logWithFlag(String message, Exception e) {
        if (ThreadLocalUtil.instance().getLogFlag()) {
            System.out.println(message);
            e.printStackTrace();
        }
    }

    public static void log(String message) {
        System.out.println(message);
    }
}
