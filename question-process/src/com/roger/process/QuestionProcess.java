package com.roger.process;

import com.roger.constant.Constant;
import com.roger.constant.QuestionType;
import com.roger.entity.ExecuteResult;
import com.roger.exception.QuestionException;
import com.roger.util.LogUtil;
import com.roger.util.MessageUtil;
import com.roger.util.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

public class QuestionProcess {
    public QuestionProcess(boolean logFlag) {
        ThreadLocalUtil.instance().setLogFlag(logFlag);
    }

    public void execute(QuestionType questionType) {
        try {
            Class questionClass = getClass(questionType.getClassName());
            Method method = getMethod(questionClass, Constant.DEFAULT_METHOD_NAME);
            QuestionInitialize initialize = new QuestionInitialize(questionType.getClassName(), method.getParameterTypes(), method.getReturnType());
            ExecuteResult result = initialize.executeCase(questionClass, method);
            LogUtil.log(String.format("%s (avg)Time use: %sms, Memory use: %sKB", questionType.getName(), result.getUseTime().toPlainString(), result.getUseMemory().toPlainString()));
            LogUtil.log(String.format("%s Case Count: %d, Pass Rate: %s", questionType.getName(), result.getCaseCount(), result.getPassRate().multiply(new BigDecimal(100)).setScale(2).toPlainString() + Constant.Character.PERCENT));
        } catch (QuestionException e) {
            LogUtil.logWithFlag(questionType.getName() + StringUtils.SPACE + e.getLocalizedMessage());
            LogUtil.log(String.format("%s Execute exception: ", questionType.getName(), e.getLocalizedMessage()));
        }
    }

    private Class getClass(String className) throws QuestionException {
        try {
            Class solution = Class.forName("com.roger.solution." + className);
            return solution;
        } catch (ClassNotFoundException e) {
            throw new QuestionException("Not exist solution class!", MessageUtil.MessageType.ERROR);
        }
    }

    private Method getMethod(Class questionClass, String methodName) throws QuestionException {
        Optional<Method> method = Arrays.stream(questionClass.getMethods()).filter(m ->
                m.getName().equals(methodName)
        ).findFirst();
        if (method.isPresent()) {
            return method.get();
        } else {
            throw new QuestionException("Not exist method in the solution class!", MessageUtil.MessageType.ERROR);
        }
    }
}
