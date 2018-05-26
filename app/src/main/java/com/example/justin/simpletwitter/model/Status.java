package com.example.justin.simpletwitter.model;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Justin on 5/9/2018.
 */

public class Status {

    private int tweetID;

    private String text;
    private String createdAt;

    private int retweetCount;
    private int favoriteCount;

    private boolean favorited;
    private boolean retweeted;

    private User user = null;

    private EntitiesHolder entities = null;

    public Status(int tweetID, String text, String createdAt,
                  int retweetCount, int favoriteCount,
                  boolean favorited, boolean retweeted) {

        this.tweetID = tweetID;
        this.text = text;
        this.createdAt = createdAt;
        this.retweetCount = retweetCount;
        this.favoriteCount = favoriteCount;
        this.favorited = favorited;
        this.retweeted = retweeted;
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

    public EntitiesHolder getEntities() {
        return entities;
    }

    public void setEntities(EntitiesHolder entities) {
        this.entities = entities;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }
}
