package com.roger.solution;

public class LongestSubstring {
    // stringbuilder 或删除或用index代替
    // 或用hashmap临时存储index 来计算长度
    public int execute(String string) {
        int index = 0;
        int max = 0;
        StringBuilder sb = new StringBuilder();
        char[] chars = string.toCharArray();
        if (chars.length == 1) return 1;
        String tmpChar;
        int start = -1;
        while (index < chars.length) {
            tmpChar = String.valueOf(chars[index]);
            int hasIndex = sb.lastIndexOf(tmpChar);
            if (hasIndex != -1) {
                start = Math.max(hasIndex, start);
            }
            sb.append(tmpChar);
            max = Math.max(sb.length() - start - 1, max);
            index++;
        }
        return max;
    }
}
