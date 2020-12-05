package com.roger.solution;

import java.util.ArrayList;
import java.util.HashMap;

public class Palindrome {
    public String execute(String s) {
        return method3(s);                                                                                                                                                                                                                                       
    }

    /**
     * 中心规划法(官方最优解）
     *
     * @param s
     * @return
     */
    private String method3(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            int len1 = isPalindromeByExtand(s, i, i);
            int len2 = isPalindromeByExtand(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > result.length()) {
                result = s.substring(i - (len - 1) / 2, i + len / 2 + 1);
            }
        }
        return result;
    }

    private int isPalindromeByExtand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return --right - ++left + 1;
    }

    /**
     * 将字符改为二维数组之后，根据前一个头尾位置的结果加上当前头尾结果判断
     *
     * @param s
     * @return
     */
    private String method2(String s) {
        boolean[][] array = new boolean[s.length()][s.length()];
        String result = "";
        // 长度从0到最长
        for (int l = 0; l < s.length(); l++) {
            for (int i = 0; i + l < s.length(); i++) {
                int j = i + l;
                if (l == 0) {
                    array[i][j] = true;
                } else if (l == 1) {
                    array[i][j] = s.charAt(i) == s.charAt(j);
                } else {
                    array[i][j] = array[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
                }
                if (array[i][j] && j - i + 1 > result.length()) {
                    result = s.substring(i, j + 1);
                }
            }
        }
        return result;
    }

    /**
     * 循环一次，记录每个字符的索引，并且利用死循环判断字符是否是回文
     *
     * @param s
     * @return
     */
    private String method1(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        HashMap<Character, ArrayList<Integer>> charMap = new HashMap<>();
        char[] chars = s.toCharArray();
        String result = null;
        ArrayList<Integer> indexs;
        for (int i = 0; i < chars.length; i++) {
            if (charMap.containsKey(chars[i])) {
                indexs = charMap.get(chars[i]);
                for (Integer index : indexs) {
                    if (isPalindrome(s, index.intValue(), i) && i - index.intValue() + 1 > (result == null ? 0 : result.length())) {
                        result = s.substring(index.intValue(), i + 1);
                        break;
                    }
                }
            } else {
                indexs = new ArrayList<>();
            }
            indexs.add(i);
            charMap.put(chars[i], indexs);
        }
        return result == null ? s.substring(0, 1) : result;
    }

    private boolean isPalindrome(String s, int start, int end) {
        while (start <= end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
}
