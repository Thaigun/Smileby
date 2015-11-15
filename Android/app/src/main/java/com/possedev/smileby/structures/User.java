package com.possedev.smileby.structures;

import java.util.Map;

/**
 * Created by Antti on 5.11.2015.
 */
public class User {
    private String beaconid; // <beaconuuid>|<beaconmajor>|<beaconminor>
    private String beaconuuid;
    private String beaconmajor;
    private String beaconminor;
    private Map<String, String> chats; //id: user

    public User() {}

    public String getBeaconid() { return beaconid; }
    public String getBeaconuuid() {
        return beaconuuid;
    }
    public String getBeaconmajor() {
        return beaconmajor;
    }
    public String getBeaconminor() {
        return beaconminor;
    }
    public Map<String, String> getChats() { return chats; }
}
