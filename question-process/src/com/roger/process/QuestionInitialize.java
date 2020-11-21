package com.roger.process;

import com.roger.constant.Constant;
import com.roger.entity.ExecuteResult;
import com.roger.entity.ListNode;
import com.roger.exception.QuestionException;
import com.roger.util.LogUtil;
import com.roger.util.MessageUtil;
import com.roger.util.ObjectParseUtil;
import com.roger.util.ReturnEqualUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionInitialize {
    // parameter type and return type
    private Class<?>[] fieldTypes;
    private List<List> params = new ArrayList<>();
    private List results = new ArrayList();

    public QuestionInitialize(String className, Class<?>[] parameterTypes, Class<?> returnType) throws QuestionException {
        String filePath = QuestionInitialize.class.getClassLoader().getResource(className + ".case").getPath();
        fieldTypes = new Class[parameterTypes.length + 1];
        System.arraycopy(parameterTypes, 0, fieldTypes, 0, parameterTypes.length);
        fieldTypes[parameterTypes.length] = returnType;
        readCase(new File(filePath));
    }

    public ExecuteResult executeCase(Class questionClass, Method method) throws QuestionException {
        if (params.isEmpty() && results.isEmpty()) {
            throw new QuestionException("No case in case file!", MessageUtil.MessageType.WARN);
        }
        if (params.size() != results.size()) {
            throw new QuestionException("No match between param and return", MessageUtil.MessageType.ERROR);
        }
        ArrayList paramList;
        int success = 0;
        int timeSum = 0, timeSumCount = 0;
        int memorySum = 0, memorySumCount = 0;
        for (int i = 0; i < params.size(); i++) {
            long startTime = System.currentTimeMillis();
            long startMemory = getUsedMemory();
            paramList = (ArrayList) params.get(i);
            boolean result = executeOne(questionClass, method, paramList.toArray(new Object[0]), results.get(i));
            success += result ? 1 : 0;
            long usedTime = System.currentTimeMillis() - startTime;
            if (usedTime > 0) {
                timeSum += usedTime;
                timeSumCount++;
            }
            long usedMemory = getUsedMemory() - startMemory;
            if (usedMemory > 0) {
                memorySum += usedMemory;
                memorySumCount++;
            }
        }
        BigDecimal result = new BigDecimal(success).divide(new BigDecimal(params.size()), 4, BigDecimal.ROUND_HALF_UP);
        BigDecimal usedTime = timeSumCount == 0 ? BigDecimal.ZERO : new BigDecimal(timeSum).divide(new BigDecimal(timeSumCount), 4, BigDecimal.ROUND_HALF_UP);
        BigDecimal usedMemory = memorySumCount == 0 ? BigDecimal.ZERO : new BigDecimal(memorySum).divide(new BigDecimal(1024), 6, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(memorySumCount), 4, BigDecimal.ROUND_HALF_UP);
        return ExecuteResult.builder().useTime(usedTime).useMemory(usedMemory).caseCount(params.size()).passRate(result);
    }

    private long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private boolean executeOne(Class questionClass, Method method, Object[] args, Object expect) throws QuestionException {
        try {
            Object result = method.invoke(questionClass.newInstance(), args);
            return compareReturn(expect, result);
        } catch (Exception e) {
            throw new QuestionException("Failed to execute solution method!", MessageUtil.MessageType.ERROR);
        }
    }

    private boolean compareReturn(Object expect, Object result) throws QuestionException {
        try {
            Class<?> type = fieldTypes[fieldTypes.length - 1];
            if (!(expect != null && result != null)) {
                return false;
            }
            if (!getClassName(expect.getClass().getName()).equals(getClassName(type.getName()))) {
                LogUtil.logWithFlag(MessageUtil.newError("Failed to parse return for expect!"));
                return false;
            }
            if (!getClassName(result.getClass().getName()).equals(getClassName(type.getName()))) {
                LogUtil.logWithFlag(MessageUtil.newError("Failed to parse return for actual!"));
                return false;
            }
            String typeName = getLastName(type.getName());
            switch (typeName) {
                case "[I":
                    return ReturnEqualUtil.equalIntArray((int[]) expect, (int[]) result);
                case "ListNode":
                    return ReturnEqualUtil.equalListNode((ListNode) expect, (ListNode) result);
                case "int":
                    return ReturnEqualUtil.equalInt(Integer.parseInt(String.valueOf(expect)), Integer.parseInt(String.valueOf(result)));
                default:
                    return ReturnEqualUtil.equalString(String.valueOf(expect), String.valueOf(result));
            }
        } catch (Exception e) {
            throw new QuestionException("Failed to parse return!", MessageUtil.MessageType.ERROR);
        }
    }

    private String getClassName(String name) {
        String className = getLastName(name);
        switch (className) {
            case "Integer":
                return "int";
            case "Double":
                return "double";
            default:
                return className;
        }
    }

    private void readCase(File caseFile) throws QuestionException {
        List paramList = new ArrayList();
        try (LineIterator it = FileUtils.lineIterator(caseFile, "UTF-8")) {
            while (it.hasNext()) {
                String line = it.nextLine();
                if (line.startsWith(Constant.Character.POUND_KEY)) {
                    continue;
                }
                if (!valid(line)) {
                    throw new QuestionException("Invalid data in case file!", MessageUtil.MessageType.ERROR);
                }
                int index = Integer.parseInt(line.substring(1, 2));
                Optional<Object> content = parse2Object(line.substring(3), index);
                if (content.isPresent()) {
                    boolean isReturn = line.startsWith("R0:");
                    if (isReturn) {
                        params.add(paramList);
                        results.add(content.get());
                        paramList = new ArrayList();
                    } else {
                        paramList.add(content.get());
                    }
                } else {
                    throw new QuestionException("Error parse for line data!", MessageUtil.MessageType.ERROR);
                }
            }
        } catch (IOException e) {
            throw new QuestionException("Error happened when read case file!", MessageUtil.MessageType.ERROR);
        } finally {
            LogUtil.logWithFlag(MessageUtil.newInfo("Finished for read case file."));
        }
    }

    private Optional<Object> parse2Object(String value, int index) {
        if (ArrayUtils.isEmpty(fieldTypes)) {
            return Optional.empty();
        }
        int typeIndex = index == 0 ? fieldTypes.length - 1 : index - 1;
        Class<?> type = fieldTypes[typeIndex];
        try {
            String typeName = getLastName(type.getName());
            switch (typeName) {
                case "int":
                    return Optional.ofNullable(ObjectParseUtil.toInt(value));
                case "[I":
                    return Optional.ofNullable(ObjectParseUtil.toIntArray(value));
                case "ListNode":
                    return Optional.ofNullable(ObjectParseUtil.toListNode(value));
                case "double":
                    return Optional.ofNullable(ObjectParseUtil.toDouble(value));
                default:
                    return Optional.ofNullable(value);
            }
        } catch (QuestionException e) {
            return Optional.empty();
        }
    }

    private String getLastName(String name) {
        return name.indexOf(Constant.Character.POINT) > 0 ? name.substring(name.lastIndexOf(Constant.Character.POINT) + 1) : name;
    }

    private boolean valid(String lineString) {
        if (StringUtils.isBlank(lineString)) {
            return false;
        }
        String[] values = lineString.split(Constant.Character.COLON, -1);
        return values.length == 2 && values[0].matches("(P[1-9])|(R0)");
    }
}
