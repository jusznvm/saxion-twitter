package com.example.justin.simpletwitter.model.entity;

/**
 * Base class for entity objects
 */

public abstract class Entity {

    private String text;
    private int startIndex;
    private int endIndex;


    public Entity(String text, int startIndex, int endIndex) {
        this.text = text;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getText() {
        return text;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public abstract String getType();

}
