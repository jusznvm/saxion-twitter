package com.example.justin.simpletwitter.model;

import static java.sql.Types.INTEGER;

/**
 * Created by Yunus on 23-5-2018.
 */

public class UserMention {

    private String screenName;
    private String name;
    private String id_str;
    private int id;
    private int startIndex;
    private int endIndex;

    public UserMention(String screenName, String name, String id_str, int id, int startIndex, int endIndex) {
        this.screenName = screenName;
        this.name = name;
        this.id_str = id_str;
        this.id = id;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "UserMention{" +
                "screenName='" + screenName + '\'' +
                ", name='" + name + '\'';
    }
}
