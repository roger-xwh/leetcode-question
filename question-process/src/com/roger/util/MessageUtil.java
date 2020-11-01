package com.roger.util;

import org.apache.commons.lang3.StringUtils;

public class MessageUtil {
    public static String newMessage(MessageType type, String message) {
        return String.format("%s %s", type.getPrefix(), message);
    }

    public static String newInfo(String message) {
        return newMessage(MessageType.INFO, message);
    }

    public static String newError(String message) {
        return newMessage(MessageType.ERROR, message);
    }

    public enum MessageType {
        ERROR, INFO, WARN;

        public String getPrefix() {
            String type = StringUtils.leftPad(this.name(), 5, StringUtils.SPACE);
            return String.format("%s - [%s]", DateUtil.logTime(), type);
        }
    }
}
