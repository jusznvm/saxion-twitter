package com.example.justin.simpletwitter.model;

import com.example.justin.simpletwitter.model.entity.Entity;

import java.util.ArrayList;

/**
 * Created by Justin on 5/9/2018.
 */

public class Status {

    private int tweetID;

    private String tweetIDString;
    private String text;
    private String createdAt;

    private int retweetCount;
    private int favoriteCount;

    private boolean favorited;
    private boolean retweeted;

    private User user = null;

    private ArrayList<Entity> entitiesList = null;

    public Status(String tweetIDString, String text, String createdAt,
                  int retweetCount, int favoriteCount,
                  boolean favorited, boolean retweeted) {

        this.tweetIDString = tweetIDString;
        this.text = text;
        this.createdAt = createdAt;
        this.retweetCount = retweetCount;
        this.favoriteCount = favoriteCount;
        this.favorited = favorited;
        this.retweeted = retweeted;
    }

    public String getTweetIDString() {
        return tweetIDString;
    }

    public int getTweetID() {
        return tweetID;
    }

    public String getText() {
        return text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setRetweetCount(int retweetCount){
        this.retweetCount = retweetCount;
    }

    public void setFavoriteCount(int favoriteCount){
        this.favoriteCount = favoriteCount;
    }

    public User getUser() {
        return user;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Entity> getEntitiesList() {
        return entitiesList;
    }

    public void setEntitiesList(ArrayList<Entity> entitiesList) {
        this.entitiesList = entitiesList;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }
}
