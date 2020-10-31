package com.roger.process;

import com.roger.constant.QuestionType;
import com.roger.exception.QuestionException;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

public class QuestionProcess {
    public void execute(QuestionType questionType) {
        String clz = "com.roger.solution." + questionType.getClassName();
        try {
            Method method = getMethod(clz, questionType.getMethodName());
            QuestionInitialize initialize = new QuestionInitialize(questionType.getClassName());
            BigDecimal result = initialize.executeCase();
            System.out.println(questionType.getName() + "通过率: " + result.multiply(new BigDecimal(100)).toPlainString());
        } catch (QuestionException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private Method getMethod(String className, String methodName) throws QuestionException {
        try {
            Class solution = Class.forName(className);
            Optional<Method> method = Arrays.stream(solution.getMethods()).filter(m ->
                    m.getName().equals(methodName)
            ).findFirst();
            if (method.isPresent()) {
                return method.get();
            } else {
                throw new QuestionException("[-ERROR-] Not exist method in the solution class!");
            }
        } catch (ClassNotFoundException e) {
            throw new QuestionException("[-ERROR-] Not exist solution class!");
        }
    }

    public static void main(String[] args) {
        new QuestionProcess().execute(QuestionType.TWO_NUMBER_SUM);
    }
}
