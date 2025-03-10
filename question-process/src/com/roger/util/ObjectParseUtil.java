package com.roger.util;

import com.roger.constant.Constant;
import com.roger.entity.ListNode;
import com.roger.exception.QuestionException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class ObjectParseUtil {
    public static int toInt(String value) throws QuestionException {
        if (StringUtils.isNotBlank(value)) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new QuestionException("Failed to parse to int.", MessageUtil.MessageType.ERROR);
            }
        }
        throw new QuestionException("NaN", MessageUtil.MessageType.ERROR);
    }

    public static int[] toIntArray(String value) throws QuestionException {
        if (value.isEmpty()) {
            return new int[0];
        }
        String[] values = value.split(Constant.Character.COMMA, -1);
        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = toInt(values[i]);
        }
        return result;
    }

    public static ListNode toListNode(String value) {
        String[] values = value.split(Constant.Character.NODE_NEXT);
        ListNode node = null;
        ListNode next = null;
        for (int i = values.length - 1; i >= 0; i--) {
            if (node != null) {
                next = node;
            }
            node = new ListNode(Integer.parseInt(values[i]), next);
        }
        return node;
    }

    public static double toDouble(String value) {
        if (value.isEmpty()) {
            return 0;
        }
        return new BigDecimal(value).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
