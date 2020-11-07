package com.roger.solution;

import com.roger.entity.ListNode;

public class TwoAdd {
    public ListNode execute(ListNode l1, ListNode l2) {
        return method1(l1, l2);
    }

    /**
     * 有两次循环
     *
     * @param l1
     * @param l2
     * @return
     */
    private ListNode method2(ListNode l1, ListNode l2) {
        int[] result = new int[0], tmp;
        int carry = 0;
        while (l1 != null || l2 != null || carry > 0) {
            int sum = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + carry;
            tmp = new int[result.length + 1];
            System.arraycopy(result, 0, tmp, 0, result.length);
            tmp[result.length] = sum % 10;
            carry = sum / 10;
            result = tmp;
            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
        }
        ListNode node = null;
        for (int i = result.length - 1; i >= 0; i--) {
            node = new ListNode(result[i], node);
        }
        return node;
    }

    private ListNode method1(ListNode l1, ListNode l2) {
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
