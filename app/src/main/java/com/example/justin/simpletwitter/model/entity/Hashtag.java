package com.example.justin.simpletwitter.model.entity;

/**
 * Created by Yunus on 18-5-2018.
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
