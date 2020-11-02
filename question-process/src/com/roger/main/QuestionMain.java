package com.roger.main;

import com.roger.constant.QuestionType;
import com.roger.process.QuestionProcess;

public class QuestionMain {
    public static void main(String[] args) {
        // 1:两数之和
        new QuestionProcess(false).execute(QuestionType.TWO_SUM);
        // 2:两数相加
        new QuestionProcess(true).execute(QuestionType.TWO_ADD);
    }
}
