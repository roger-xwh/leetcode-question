package com.roger.constant;

public enum QuestionType {
    TWO_SUM("[两数之和]", "TwoSum", "twoSum"),
    TWO_ADD("[两数相加]", "TwoAdd", "twoAdd");
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
