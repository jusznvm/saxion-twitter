package com.example.justin.simpletwitter.model;

/**
 * Created by Justin on 5/10/2018.
 */

public class DirectMessage {

    private int recipientID;
    private int senderID;
    private int dmID;
    private int timestampCreated;

    private String text;

    public DirectMessage(int recipientID, int senderID,
                         int dmID, int timestampCreated,
                         String text) {

        this.recipientID = recipientID;
        this.senderID = senderID;
        this.dmID = dmID;
        this.timestampCreated = timestampCreated;
        this.text = text;

    }


}
