package com.possedev.smileby.helper_classes;

import com.possedev.smileby.structures.Message;
import com.possedev.smileby.structures.User;

/**
 * Created by Antti on 16.11.2015.
 */
public class Chat {
    private String key;
    private String friend;
    private Message[] messages;

    public Chat(String keyStr, String friendStr) {
        key = keyStr;
        friend = friendStr;
    }

    public String getKey() { return key; }
    public String getFriend() { return friend; }
    public Message[] getMessages() { return messages; }
}
