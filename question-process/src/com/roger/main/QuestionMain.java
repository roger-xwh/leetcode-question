package com.roger.main;

import com.roger.constant.QuestionType;
import com.roger.process.QuestionProcess;

public class QuestionMain {
    public static void main(String[] args) {
        // 1:两数之和
        //new QuestionProcess(false).execute(QuestionType.TWO_SUM);
        // 2:两数相加
        //new QuestionProcess(false).execute(QuestionType.TWO_ADD);
        // 3:无重复字符的最长子串
        //new QuestionProcess(true).execute(QuestionType.LONGEST_SUBSTRING);
        // 4:寻找两个正序数组的中位数
        // new QuestionProcess(true).execute(QuestionType.FIND_MEDIAN);
        // 5:最长回文子串
        // new QuestionProcess(true).execute(QuestionType.PALINDROME);
        // 6:Z字形变换
//        new QuestionProcess(true).execute(QuestionType.Z_PRINT);
        // 7:整数反转
        new QuestionProcess(true).execute(QuestionType.NUMBER_REVERSE);
    }
}
