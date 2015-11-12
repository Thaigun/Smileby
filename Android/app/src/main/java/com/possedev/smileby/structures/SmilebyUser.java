package com.possedev.smileby.structures;

import java.util.List;

/**
 * Created by Antti on 5.11.2015.
 */
public class SmilebyUser {
    private String username;
    private String beaconuuid;
    private String beaconmajor;
    private String beaconminor;
    private List<SmilebyUserChat> chats;

    public SmilebyUser() {}

    public String getUsername() {
        return username;
    }
    public String getBeaconuuid() {
        return beaconuuid;
    }
    public String getBeaconmajor() {
        return beaconmajor;
    }
    public String getBeaconminor() {
        return beaconminor;
    }
    public List<SmilebyUserChat> getChats() {
        return chats;
    }
}
