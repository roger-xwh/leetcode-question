package com.roger.solution;

import java.util.ArrayDeque;
import java.util.Queue;

public class NumberReverse {
    public int execute(int x) {
        return method4(x);
    }

    /**
     * 利用队列(FIFO)特性 - 搞复杂了
     *
     * @param x
     * @return
     */
    private int method4(int x) {
        Queue<Integer> queue = new ArrayDeque<>(12);
        while (x != 0) {
            queue.offer(Integer.valueOf(x % 10));
            x /= 10;
        }
        int result = 0;
        int limit = Integer.MAX_VALUE / 10;
        while (queue.size() > 0) {
            int number = queue.poll().intValue();
            if (result > limit || result == limit && number > 7
                    || result < limit * -1 || result == limit * -1 && number < -8) {
                result = 0;
                break;
            }
            result = result * 10 + number;
        }
        return result;
    }

    /**
     * 方法2的优化版
     *
     * @param x
     * @return
     */
    private int method3(int x) {
        int sign = x < 0 ? -1 : 1;
        int result = 0;
        int limit = Integer.MAX_VALUE / 10;
        while (x != 0) {
            int curr = x % 10 * sign;
            if (result > limit || result == limit && curr > (sign == -1 ? 8 : 7)) {
                result = 0;
                break;
            }
            result = result * 10 + curr;
            x /= 10;
        }
        return result * sign;
    }

    /**
     * 不利用StringBuilder，利用取模的方式
     *
     * @param x
     * @return
     */
    private int method2(int x) {
        int result = 0;
        int tmpNumber = x;
        int plusLimit = Integer.MAX_VALUE / 10;
        int minusLimit = Integer.MIN_VALUE / 10;
        while (tmpNumber != 0) {
            int curr = tmpNumber % 10;
            if (result > plusLimit || result == plusLimit && curr > 7
                    || result < minusLimit || result == minusLimit && curr < -8) {
                result = 0;
                break;
            }
            result = result * 10 + curr;
            tmpNumber = tmpNumber / 10;
        }
        return result;
    }

    /**
     * StringBuilder的reverse方式
     *
     * @param x
     * @return
     */
    private int method1(int x) {
        int sign = x < 0 ? -1 : 1;
        long value = (long) x * sign;
        StringBuilder sb = new StringBuilder(String.valueOf(value));
        sb.reverse();
        long longValue = Long.parseLong(sb.toString()) * sign;
        return longValue < Integer.MIN_VALUE || longValue > Integer.MAX_VALUE ? 0 : (int) longValue;
    }
}
