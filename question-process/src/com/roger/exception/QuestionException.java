package com.roger.exception;

import com.roger.util.MessageUtil;

public class QuestionException extends Exception {
    public QuestionException(String message, MessageUtil.MessageType msgType) {
        super(MessageUtil.newMessage(msgType, message));
    }
}
