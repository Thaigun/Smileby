package com.possedev.smileby.structures;

/**
 * Created by Antti on 16.11.2015.
 */
public class Message {
    private String sender;
    private String message;

    //Firebase requires an empty constructor,
    public Message() {

    }

    public Message(String senderStr, String messageStr) {
        sender = senderStr;
        message = messageStr;
    }

    public String getSender() { return sender; }
    public String getMessage() { return message; }
}
