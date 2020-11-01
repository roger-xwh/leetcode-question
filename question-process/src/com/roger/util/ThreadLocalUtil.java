package com.roger.util;

public final class ThreadLocalUtil {
    private static ThreadLocalUtil util = new ThreadLocalUtil();
    private ThreadLocal<Boolean> LOG_FLAG = new ThreadLocal<>();

    private ThreadLocalUtil() {
    }

    public static ThreadLocalUtil instance() {
        return util;
    }

    public void setLogFlag(boolean flag) {
        LOG_FLAG.set(flag);
    }

    public boolean getLogFlag() {
        return LOG_FLAG.get() == null ? false : LOG_FLAG.get().booleanValue();
    }
}
