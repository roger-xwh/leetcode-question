package com.roger.constant;

public enum QuestionType {
    TWO_SUM("[Two Number Sum]", "TwoSum"),
    TWO_ADD("[Two Number Add]", "TwoAdd"),
    LONGEST_SUBSTRING("[Longest Substring]", "LongestSubstring"),
    FIND_MEDIAN("[Find Median Sorted Array]", "FindMedian"),
    PALINDROME("[Longest Palindrome]", "Palindrome");
    private String name;
    private String className;

    QuestionType(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }
}
