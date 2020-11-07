package com.roger.util;

import com.roger.entity.ListNode;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class ReturnEqualUtil {
    public static boolean equalIntArray(int[] expext, int[] actual) {
        return Arrays.equals(expext, actual);
    }

    public static boolean equalString(String expect, String actual) {
        return StringUtils.equals(expect, actual);
    }

    public static boolean equalListNode(ListNode expect, ListNode actual) {
        if (expect == null && actual == null) {
            return true;
        } else if (expect == null || actual == null) {
            return false;
        } else {
            ListNode node1 = expect;
            ListNode node2 = actual;
            while (node1 != null || node2 != null) {
                if (node1 == null || node2 == null) {
                    return false;
                }
                if (node1.val != node2.val) {
                    return false;
                }
                node1 = node1.next;
                node2 = node2.next;
            }
            return node1 == null && node2 == null;
        }
    }

    public static boolean equalInt(int expect, int actual) {
        return expect == actual;
    }
}
