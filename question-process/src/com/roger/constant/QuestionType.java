package com.roger.constant;

public enum QuestionType {
    TWO_NUMBER_SUM("[两数之和]", "TwoNumberSum", "twoSum");
    private String name;
    private String className;
    private String methodName;

    QuestionType(String name, String className, String methodName) {
        this.name = name;
        this.className = className;
        this.methodName = methodName;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }
}
