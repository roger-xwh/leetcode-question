package com.roger.process;

import com.roger.exception.QuestionException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class QuestionInitialize {
    public QuestionInitialize(String className) throws QuestionException {
        String filePath = QuestionInitialize.class.getClassLoader().getResource(className + ".case").getPath();
        readCase(new File(filePath));
    }

    public BigDecimal executeCase() {
        return null;
    }

    private void readCase(File caseFile) throws QuestionException {
        try (LineIterator it = FileUtils.lineIterator(caseFile, "UTF-8")) {
            while (it.hasNext()) {
                String line = it.nextLine();
                // do something with line
            }
        } catch (IOException e) {
            throw new QuestionException("[-ERROR-] Error happened when read case file!");
        } finally {
            System.out.println("[- INFO-] Finished for read case file.");
        }
    }
}
