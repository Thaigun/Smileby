package com.possedev.smileby.helper_classes;

import com.possedev.smileby.structures.Message;
import com.possedev.smileby.structures.User;

/**
 * Created by Antti on 16.11.2015.
 */
public class Chat {
    private String key;
    private User friend;
    private Message[] messages;

    public Chat(String keyStr) {
        key = keyStr;
    }

    public User getFriend() { return friend; }
    public Message[] getMessages() { return messages; }
}
