package com.roger.solution;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZPrint {
    public String execute(String s, int rows) {
        if (rows == 1) {
            return s;
        }
        return method4(s, rows);
    }

    private String method4(String s, int rows) {
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        int count = rows * 2 - 2;
        int groups = (len - 1) / count + 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < groups; j++) {
                int index = j * count + i;
                sb.append(index < len ? s.charAt(index) : "");
                if (i > 0 && i < rows - 1) {
                    index += count - i - i;
                    sb.append(index < len ? s.charAt(index) : "");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 空间利用率比method2好
     *
     * @param s
     * @param numRows
     * @return
     */
    private String method3(String s, int numRows) {
        StringBuilder[] rows = Stream.iterate(0, i -> i + 1).limit(numRows).parallel()
                .map(index -> new StringBuilder()).collect(Collectors.toList()).toArray(new StringBuilder[0]);
        int currentRow = 0;
        boolean moveDown = false;
        for (char c : s.toCharArray()) {
            rows[currentRow].append(c);
            if (currentRow == 0 || currentRow == numRows - 1) moveDown = !moveDown;
            currentRow += moveDown ? 1 : -1;
        }
        return Arrays.stream(rows).collect(Collectors.joining());
    }

    /**
     * V字形变换
     *
     * @param s
     * @param rows
     * @return
     */
    private String method2(String s, int rows) {
        String[][] array = new String[rows][s.length()];
        int i = 0, j = 0;
        boolean moveDown = false;
        for (char c : s.toCharArray()) {
            array[i][j] = String.valueOf(c);
            if (i == 0) {
                moveDown = true;
            } else if (i == rows - 1) {
                moveDown = false;
            }
            if (moveDown) {
                i++;
            } else {
                i--;
            }
            j++;
        }
        return Arrays.stream(array).map(a -> Arrays.stream(a).filter(ss -> ss != null).collect(Collectors.joining())).collect(Collectors.joining());
    }

    /**
     * 依据题目标准循环打印（二维数组）
     *
     * @param s
     * @param rows
     * @return
     */
    private String method1(String s, int rows) {
        int countByGroup = s.length() * 2 - 2;
        int group = (s.length() - 1) / (countByGroup) + 1;
        int cols = group * (countByGroup);
        String[][] array = new String[rows][cols];
        int i = 0, j = 0;
        boolean moveDown = false;
        for (char c : s.toCharArray()) {
            array[i][j] = String.valueOf(c);
            if (i == 0) {
                moveDown = true;
            } else if (i == rows - 1) {
                moveDown = false;
            }
            if (moveDown) {
                i++;
            } else {
                i--;
                j++;
            }
        }
        return Arrays.stream(array).map(a -> Arrays.stream(a).filter(ss -> ss != null).collect(Collectors.joining())).collect(Collectors.joining());
    }
}
