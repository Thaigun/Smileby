package com.possedev.smileby.structures;

/**
 * Created by Antti on 14.11.2015.
 */
public class Encounter {
    // the user1 and user2 should always be in alphabetical order
    private String user1;
    private String user2;
    private int encounters;

    public Encounter() {

    }

    public String getUser1() { return user1; }
    public String getUser2() { return user2; }
    public int getEncounters() { return encounters; }
}
