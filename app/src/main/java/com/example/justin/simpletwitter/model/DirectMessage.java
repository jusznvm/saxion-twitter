package com.example.justin.simpletwitter.model;

import com.example.justin.simpletwitter.model.entity.Entity;

import java.util.ArrayList;

/**
 * Model class that contains a respresentation of a DirectMessage
 */

public class DirectMessage {

    private long recipientID;
    private long senderID;
    private long dmID;

    private long timestampCreated;

    private String text;

    private ArrayList<Entity> entities;


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

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
}
