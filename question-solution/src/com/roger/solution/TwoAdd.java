package com.roger.solution;

import com.roger.entity.ListNode;

public class TwoAdd {
    public ListNode twoAdd(ListNode l1, ListNode l2) {
        ListNode result, cur;
        cur = result = new ListNode(0);
        int carry = 0;
        while (l1 != null || l2 != null || carry > 0) {
            int sum = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + carry;
            cur = cur.next = new ListNode(sum % 10);
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        return result.next;
    }
}
