package com.example.justin.simpletwitter.model;

import java.util.ArrayList;

/**
 * Created by Justin on 5/10/2018.
 */

public class DirectMessage {

    private long recipientID;
    private long senderID;
    private long dmID;

    private long timestampCreated;

    private String text;

    private ArrayList<Entity> dmEntities = null;


    public DirectMessage(long recipientID, long senderID,
                         long dmID, long timestampCreated,
                         String text) {

        this.recipientID = recipientID;
        this.senderID = senderID;
        this.dmID = dmID;
        this.timestampCreated = timestampCreated;
        this.text = text;

    }

    public String getText() {
        return text;
    }

    public ArrayList<Entity> getHashtagEntities() {
        return dmEntities;
    }

    public void setHashtagEntities(ArrayList<Entity> hashtagEntities) {
        this.dmEntities = hashtagEntities;


    }
}
