package com.example.justin.simpletwitter.model;

/**
 * Created by Yunus on 18-5-2018.
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

    public void setText(String text) {
        this.text = text;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "text='" + text + '\'' +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                '}';
    }
}
