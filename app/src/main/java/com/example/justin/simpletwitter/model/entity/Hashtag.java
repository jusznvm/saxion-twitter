package com.example.justin.simpletwitter.model.entity;

/**
 * Model class for a hashtag entity
 */

public class Hashtag extends Entity {


    public Hashtag(String text, int startIndex, int endIndex) {
        super(text, startIndex, endIndex);
    }

    @Override
    public String getType() {
        return "Hashtag";
    }
}
