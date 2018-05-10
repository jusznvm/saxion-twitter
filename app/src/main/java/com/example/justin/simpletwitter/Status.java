package com.example.justin.simpletwitter;

/**
 * Created by Justin on 5/9/2018.
 */

public class Status {

    private String text;
    private String createdAt;

    private int retweetCount;
    private int favoriteCount;

    private boolean favorited;
    private boolean retweeted;

    private User user = null;

    public Status(String text, String createdAt,
                  int retweetCount, int favoriteCount,
                  boolean favorited, boolean retweeted) {

        this.text = text;
        this.createdAt = createdAt;
        this.retweetCount = retweetCount;
        this.favoriteCount = favoriteCount;
        this.favorited = favorited;
        this.retweeted = retweeted;
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
}
